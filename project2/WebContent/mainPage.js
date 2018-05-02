function handleGenreResult(resultData) {
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
	                '<a href="movie_list.html?genre=' + resultData[i+j]['genre'] + '">'
	                + resultData[i+j]["genre"] +     // display star_name for the link text
	                '</a>' +
	                "</td>";
    		}
    		rowHTML += "</tr>";
    		table.append(rowHTML);
    		console.log(rowHTML);
    	}
    }
    	
    	let table2 = jQuery ('#letterTable');

    	var letterArray = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
    	var numberArray = '1234567890'.split('');
    	let rowHTML = "<tr>";
    	for(let i = 0; i < letterArray.length; i++){
    				if(i % 13 == 0)
    					{
    					rowHTML += "</tr><tr>";
    					}
    				rowHTML += "<td><a href='movie_list.html?letter=" + letterArray[i] + "'>"
    				rowHTML += letterArray[i] + "</a></td>";
    			}
    			rowHTML += "</tr><tr>";
    			for(let i = 0; i < numberArray.length; i++)
    				{
    				rowHTML += "<td><a href='movie_list.html?letter=" + numberArray[i] + "'>"
    				rowHTML += numberArray[i] + "</a></td>";
    				}
    			table2.append(rowHTML);
    }


// Makes the HTTP GET request and registers on success callback function handleStarResult
jQuery.ajax({
    dataType: "json", // Setting return data type
    method: "GET", // Setting request method
    url: "api/genres", // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleGenreResult(resultData) 
    		// Setting callback function to handle data returned successfully by the StarsServlet
});
