// D3 SVG GRAPH
var svgWidth = 850, svgHeight = 500;

d3.json("http://localhost:8080/node", function(error, json) {
    if (error) return console.warn(error);

    var csv = ConvertToCSV(json);

    var links = d3.csvParse(csv);

    // list of all attacks
    var labels = links.map(l => `${[l[links.columns[0]]]}`);

    d3.json("http://localhost:8080/attacks", function(error, attacks) {
        if (error) return console.warn(error);

        var svg = d3.select('svg')
        .graphviz()
            .renderDot(`digraph {
                size=8;
                graph [bgcolor=black];
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
        // .map(l => [l[links.columns[1]], l[links.columns[0]]])
        .filter(([source, target]) => source && target)
        .map(([source, target]) => [labels.indexOf(source), labels.indexOf(target)])
        .map(([source, target]) => `${source} -> ${target} [color="white"]`)
        .join("; ");
}



function redGreenLabels(label, index, attacks) {

    var newLabel = `${index} [label = "${label}" color="limegreen" fontcolor="limegreen"]`;

    attacks.forEach(function(item, i) {
        if (item.name === label) {
            if(item.isLikely === false) {
                newLabel = `${index} [label = "${label}" color="red" fontcolor="red"]`;
            } else {
                newLabel = `${index} [label = "${label}" color="limegreen" fontcolor="limegreen"]`;
            }
        }
    });

    console.log(newLabel);
    return newLabel;


}

// Prepares array of labels to be used in Graphviz digraph
function labelMaker(labels, attacks) {

    return labels
        .map((label, index) => redGreenLabels(label, index, attacks))
        .join("\n");


}

// Helper function for converting JSON to CSV
function ConvertToCSV(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = '';

    for (var i = 0; i < array.length; i++) {
        var line = '';
        for (var index in array[i]) {
            if (line != '') line += ','

            line += array[i][index];
        }

        str += line + '\r\n';
    }

    return str;
}




// Hierarchical Tree

function createHierarchicalTree(links) {
    var rootObject = links.filter(([source, target]) => !source && target);
    var root = rootObject.map(([source, target]) => {
        return target;
    });
    var rootNode = new TreeNode(root + ""); 
    findChildren(links, rootNode);

    // Customize icons for tree
    tree = new TreeView(rootNode, "#container",{
        context_menu: undefined
    });
}



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


// AJAX REQUEST - CATEGORY/MEMBER DROP DOWNS
function sendAjaxRequest() {
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

// JQuery - uses sendAjaxRequest when changing category drop down
$(document).ready(function() {
    // if parent list not empty, disable category button
    if ($("#capec-parent").children('option').length > 1) {
        $("#category").attr("disabled", true);
    } else if ($("#capec-parent").children('option').length === 1) {
        $("#capec-parent").attr("disabled", true);
    }
    $("#category").change(function() {
        domainSelected = false;
        sendAjaxRequest();
    });
    $("#capec-parent").change(function() {
        sendAjaxRequest();
    });
    
});


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
