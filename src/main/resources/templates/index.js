var svgWidth = 850, svgHeight = 400;

d3.json("http://localhost:8080/node", function(error, json) {
    if (error) return console.warn(error);
    console.log(json);

    var csv = ConvertToCSV(json);
    console.log(csv);

    var links = d3.csvParse(csv);

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
    console.log(root + "");
    var rootNode = new TreeNode(root + ""); 
    findChildren(links, rootNode);

    // Customize icons for tree
    var tree = new TreeView(rootNode, "#container",{
        leaf_icon: "<span>&#128441;</span>",
        parent_icon: "<span>&#128449;</span>",
        open_icon: "<span>&#9698;</span>",
        close_icon: "<span>&#9654;</span>"
    });
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




function openForm() {
    document.getElementById("myForm").style.display = "block";
    //window.open("localhost:8080/form", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
}
  
function closeForm() {
    document.getElementById("myForm").style.display = "none";
}

// function storeNewAttack() {
//     console.log("here");
//     var x = document.getElementById("frm1");
//     var text = "";
//     var i;
//     for (i = 0; i < x.length; i++) {
//         text += x.elements[i].value + "<br>";
//     }
//     document.getElementById("info").innerHTML = text;
// }


// function includeHTML() {
//     var z, i, elmnt, file, xhttp;
//     /* Loop through a collection of all HTML elements: */
//     z = document.getElementsByTagName("*");
//     for (i = 0; i < z.length; i++) {
//       elmnt = z[i];
//       /*search for elements with a certain atrribute:*/
//       file = elmnt.getAttribute("w3-include-html");
//       if (file) {
//         /* Make an HTTP request using the attribute value as the file name: */
//         xhttp = new XMLHttpRequest();
//         xhttp.onreadystatechange = function() {
//           if (this.readyState == 4) {
//             if (this.status == 200) {elmnt.innerHTML = this.responseText;}
//             if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
//             /* Remove the attribute, and call this function once more: */
//             elmnt.removeAttribute("w3-include-html");
//             includeHTML();
//           }
//         }
//         xhttp.open("GET", file, true);
//         xhttp.send();
//         /* Exit the function: */
//         return;
//       }
//     }
//   }
