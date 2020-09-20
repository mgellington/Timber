// D3 SVG GRAPH
var svgWidth = 850, svgHeight = 500;

// Loads tree from JSON at /node
d3.json("http://localhost:8080/node", function(error, json) {
    if (error) return console.warn(error);

    // Converts JSON to CSV and parses
    var csv = csvConverter(json);
    var links = d3.csvParse(csv);

    // list of all attacks
    var labels = links.map(l => `${[l[links.columns[0]]]}`);

    // Loads list of CAPEC attacks so likelihood can be accounted for
    d3.json("http://localhost:8080/attacks", function(error, attacks) {
        if (error) return console.warn(error);

        // Renders Tree as SVG using GRAPHVIZ
        var svg = d3.select('svg')
        .graphviz()
            .renderDot(`digraph {
                ranksep=1;
                size=8;
                graph [bgcolor=black];
                node [fontname = "helvetica"];
                ${labelMaker(labels, attacks)}
                ${replaceWithID(links, labels)}
            }`);
    })
    

});

// replaces name of attack with an id so that they can be
// mapped onto the tree; then translates list into DOT language
function replaceWithID(links, labels) {
    var newLinks = links.map(l => [l[links.columns[1]], l[links.columns[0]]]);

    // function for adding labels to hierarchical tree
    createHierarchicalTree(newLinks);


    return newLinks
        .filter(([source, target]) => source && target)
        .map(([source, target]) => [labels.indexOf(source), labels.indexOf(target)])
        .map(([source, target]) => `${source} -> ${target} [color="white"]`)
        .join("; ");
}



function colorLabels(label, index, attacks) {

    // DEFAULT COLOR = WHITE
    var newLabel = `${index} [label = "${label}" color="white" fontcolor="white"]`;

    attacks.forEach(function(item, i) {
        if (item.name === label) {

            // HIGH LIKELIHOOD = RED
            if(item.likelihood === "High") {
                newLabel = `${index} [label = "${label}" color="red" fontcolor="red"]`;
            }

            // MEDIUM LIKELIHOOD = YELLOW
            else if (item.likelihood === "Medium") {
                newLabel = `${index} [label = "${label}" color="yellow" fontcolor="yellow"]`;
            } 

            // LOW LIKELIHOOD = GREEN
            else if (item.likelihood === "Low") {
                newLabel = `${index} [label = "${label}" color="limegreen" fontcolor="limegreen"]`;
            }
        }
    });

    return newLabel;


}

// Prepares array of labels to be used in Graphviz digraph
function labelMaker(labels, attacks) {

    return labels
        .map((label, index) => colorLabels(label, index, attacks))
        .join("\n");


}

// Helper function for converting JSON to CSV
function csvConverter(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = '';

    for (var i = 0; i < array.length; i++) {
        var line = '';
        for (var index in array[i]) {
            if (line != '') line += ','

            // ensures entire title is included if commas present in attack name
            line += "\"" + array[i][index] + "\"";
        }

        str += line + '\r\n';
    }

    return str;
}




// Hierarchical Tree (TreeJS)
function createHierarchicalTree(links) {
    var rootObject = links.filter(([source, target]) => !source && target);
    var root = rootObject.map(([source, target]) => {
        return target;
    });
    var rootNode = new TreeNode(root + ""); 
    findChildren(links, rootNode);

    tree = new TreeView(rootNode, "#container",{
        context_menu: undefined
    });
}


// Uses list of attacks to recursively add child nodes to parent in hierarchical tree
function findChildren(links, rootNode) {
    var filteredArray = links.filter(([source, target]) => source === rootNode.toString());
    var childArray = filteredArray.map(([source, target]) => `${target}`);
    if (childArray.length > 0) {
        childArray.forEach((childString) => {
            var currentChild = new TreeNode(childString.toString());
            rootNode.addChild(currentChild);
            findChildren(links, currentChild);
        })
    }

}

var domainSelected = true;


// AJAX REQUESTS - CATEGORY/ATTACK DROP DOWNS
function sendAjaxRequest() {

    // Supplies list of members based on category selected to drop down list
    if (!domainSelected) {
        var category = $("#category").val();
        $.getJSON("http://localhost:8080/members", function(data) {
            $("#member").empty();
            data.forEach(function(item, i) {
                if (item.name == category) {
                    var members = item.members;
                    members.forEach(function(item, i) {
                        var option = `<option value= "${item}"> ${item} </option>`;
                        $("#member").append(option);
                    });
                }
                
            });
        });

    // Supplies list of suggested children based on category selected to drop down list
    } else {
        var parent = $("#capec-parent").val();
        $.getJSON("http://localhost:8080/attacks", function(data) {
            $("#member").empty();
            data.forEach(function(item, i) {
                if(item.name == parent) {
                    var children = item.childAttacks;
                    children.forEach(function(item, i) {
                        var option = `<option value= "${item}"> ${item} </option>`;
                        $("#member").append(option);
                    });
                }
            });
        });
    }
    
};

// Updates information box from node selected in hierarchical tree on button press
function updateInfoBox(node) {
    $.getJSON("http://localhost:8080/attacks", function(data) {
        $("#info-box").empty();
        data.forEach(function(item, i) {
            if (node.toString() == item.name) {
                $("#info-box").append(`<h5>Attack Info</h5>`);
                $("#info-box").append(`<p>CAPEC ID: ${item.capecID}</p>`);
                $("#info-box").append(`<p>NAME: ${item.name}</p>`);
                $("#info-box").append(`<p>DESCRIPTION: ${item.description}</p>`);
                $("#info-box").append(`<p>LIKELIHOOD OF ATTACK: ${item.likelihood}</p>`);
                $("#info-box").append(`<p>SEVERITY: ${item.severity}</p>`);
                $("#info-box").append(`<p>For more information, go to capec.mitre.org/data/definitions/${item.capecID}.html</p>`);
            }
        })
    })
}

// JQuery - uses sendAjaxRequest when changing drop downs
$(document).ready(function() {

    // if parent list not empty, adds list of parents to array
    if ($("#capec-parent").children('option').length > 1) {
        const currentAttacks = [];
        $.each($("#capec-parent").children('option'), function(){
            currentAttacks.push($(this).val());
        });

        // if any attacks already from CAPEC list, disable Domain drop down
        $.getJSON("http://localhost:8080/attacks", function(data) {
            data.forEach(function(item, i) {
                Array.from(currentAttacks).forEach(function(attack, a) {
                    if (item.name == attack) {
                        $("#category").attr("disabled", true);
                        return false;
                    }
                });
            });
        });
        
    // There are actually no available parents--disables ability to choose parents
    } else if ($("#capec-parent").children('option').length === 1) {
        $("#capec-parent").attr("disabled", true);
    }

    if($("#custom-parent").children('option').length === 1) {
        $("#custom-parent").attr("disabled", true);
    }

    // Sends ajax request if domain selection is changed
    $("#category").change(function() {
        domainSelected = false;
        sendAjaxRequest();
    });

    // Sends ajax request if CAPEC parent selection is changed
    $("#capec-parent").change(function() {
        sendAjaxRequest();
    });
    
});


// BUTTON EVENTS

function getInfo() {
    var nodes = tree.getSelectedNodes();
    nodes.forEach(function(node, i) {
        updateInfoBox(node);
    });
}

function openForm() {
    document.getElementById("myForm").style.display = "block";
}
  
function closeForm() {
    document.getElementById("myForm").style.display = "none";
}

function openCapecForm() {
    document.getElementById("capecForm").style.display = "block";
}
  
function closeCapecForm() {
    document.getElementById("capecForm").style.display = "none";
}

function openDeleteForm() {
    document.getElementById("deleteForm").style.display = "block";
}

function closeDeleteForm() {
    document.getElementById("deleteForm").style.display = "none";
}

function reloadPage() {
    // waits for form to submit before reloading page
    setTimeout(function(){window.location.reload();},100);
}
