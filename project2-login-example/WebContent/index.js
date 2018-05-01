function handleStarResult(resultData) {
    console.log("handleStarResult: populating star table from resultData");

    // Populate the star table
    // Find the empty table body by id "star_table_body"
    let table = jQuery("#genreTable");

    for(let i = 0; i < resultData.length; i++){
    	if(i % 4 == 0){
    		let rowHTML = "<tr>";
    		for (let j = 0; j < 4 && i+j < resultData.length; j++) {
	            rowHTML +=
	                "<td>" +
	                // Add a link to single-star.html with id passed with GET url parameter
	                '<a href="browse.html?genreid=' + resultData[i+j]['genreId'] + '">'
	                + resultData[i+j]["genreName"] +     // display star_name for the link text
	                '</a>' +
	                "</td>";
    		}
    		rowHTML += "</tr>";
    		table.append(rowHTML);
    		console.log(rowHTML);
    	}
    }
}
    


// Makes the HTTP GET request and registers on success callback function handleStarResult
jQuery.ajax({
    dataType: "json", // Setting return data type
    method: "GET", // Setting request method
    url: "api/genres", // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleStarResult(resultData) // Setting callback function to handle data returned successfully by the StarsServlet
});

