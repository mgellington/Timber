// D3 SVG GRAPH
var svgWidth = 850, svgHeight = 400;

d3.json("http://localhost:8080/node", function(error, json) {
    if (error) return console.warn(error);
    console.log(json);

    var csv = ConvertToCSV(json);
    console.log(csv);

    var links = d3.csvParse(csv);

    // list of all attacks
    var labels = links.map(l => `${[l[links.columns[0]]]}`);

    var svg = d3.select('svg')
    .graphviz()
        .renderDot(`digraph {
            graph [bgcolor=black];
            ${labelMaker(labels)}
            ${replaceWithID(links, labels)}
        }`);

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
        .map(([source, target]) => `${source} -> ${target} [color="limegreen"]`)
        .join("; ");
}

// Prepares array of labels to be used in Graphviz digraph
function labelMaker(labels) {
    return labels
        .map((label, index) => `${index} [label = "${label}" color="limegreen" fontcolor="limegreen"]`)
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
    var tree = new TreeView(rootNode, "#container",{
        leaf_icon: "<span>&#128441;</span>",
        parent_icon: "<span>&#128449;</span>",
        open_icon: "<span>&#9698;</span>",
        close_icon: "<span>&#9654;</span>"
    });

    // var parent = findParent(tree);
}



function findChildren(links, rootNode) {
    var filteredArray = links.filter(([source, target]) => source === rootNode.toString());
    console.log(filteredArray);
    var childArray = filteredArray.map(([source, target]) => `${target}`);
    console.log(childArray);
    if (childArray.length > 0) {
        childArray.forEach((childString) => {
            var currentChild = new TreeNode(childString.toString());
            rootNode.addChild(currentChild);
            findChildren(links, currentChild);
        })
    }

}


// AJAX REQUEST - CATEGORY/MEMBER DROP DOWNS
function sendAjaxRequest() {
    var category = $("#category").val();
    $.getJSON("http://localhost:8080/members", function(data) {
        $("#member").empty();
            data.forEach(function(item, i) {
                if (item.name == category) {
                    var members = item.members;
                    members.forEach(function(item, i) {
                        var option = `<option value= "${item}"> ${item} </option>`;
                        $("#member").append(option);
                    })
                }
                
            });


    });
    
};

// JQuery - uses sendAjaxRequest when changing category drop down
$(document).ready(function() {
    $("#domain-button").click(function() {
        sendAjaxRequest();
    });
});




function openForm() {
    document.getElementById("myForm").style.display = "block";
}
  
function closeForm() {
    document.getElementById("myForm").style.display = "none";
}

function openDomainForm() {
    document.getElementById("domainForm").style.display = "block";
}

function closeDomainForm() {
    document.getElementById("domainForm").style.display = "close";
}

function openCapecForm() {
    document.getElementById("capecForm").style.display = "block";
    closeDomainForm();
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


