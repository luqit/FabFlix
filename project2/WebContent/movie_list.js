/**
 * 
 */
function getParameterByName(target) {
    // Get request URL
    let url = window.location.href;
    // Encode target parameter name to url encoding
    target = target.replace(/[\[\]]/g, "\\$&");

    // Ues regular expression to find matched parameter value
    let regex = new RegExp("[?&]" + target + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    

    // Return the decoded parameter value
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}


/**
 * Handles the data returned by the API, read the jsonObject and populate data into html elements
 * @param resultData jsonObject
 */

function handleResult(resultData) {
	/*
    console.log("handleResult: populating star info from resultData");
    // populate the star info h3
    //find the empty h3 body by id "star_info"
    let starInfoElement = jQuery("#star_info");

    // append two html <p> created to the h3 body, which will refresh the page
    starInfoElement.append("<p>Star Name: " + resultData[0]["star_name"] + "</p>" +
        "<p>Date Of Birth: " + resultData[0]["star_dob"] + "</p>");
	
    console.log("handleResult: populating movie table from resultData");
    */

    // Populate the star table
    // Find the empty table body by id "movie_table_body"
    let movieTableBodyElement = jQuery("#movie_table_body");
    
    // Concatenate the html tags with resultData jsonObject to create table rows
    for (let i = 0; i < Math.min(10, resultData.length); i++) {
        let rowHTML = "";
        rowHTML += "<tr>";
        rowHTML += "<th>" + resultData[i]["movieId"] + "</th>";
        rowHTML += "<th><a href='single-movie.html?id=" + resultData[i]["movieId"]; 
        rowHTML += "&title=" + resultData[i]["movieTitle"] + "&year=" + resultData[i]["year"];
        rowHTML += "&director=" + resultData[i]["movieDirector"] + resultData[i]["genreNames"];
        rowHTML += "&starNames=" + resultData[i]["starNames"] + "&rating=" + resultData[i]["rating"];
        rowHTML += "'>" + resultData[i]["movieTitle"] + "</a></th>";
        rowHTML += "<th>" + resultData[i]["movieYear"] + "</th>";
        rowHTML += "<th>" + resultData[i]["movieDirector"] + "</th>";
        rowHTML += "<th>" + resultData[i]["genreNames"] + "</th>";
        rowHTML += "<th>";//<a href='single-star.html?starName=" + resultData[i]["starNames"] + "'>" + resultData[i]["starNames"] + "</a>";
        /*
        var star = (resultData[i]["starNames"]);
        var starArray = star.split(',');
        var starLength = starArray.length;
        console.log(starLength);
        for(var i = 0; i < starLength; i++)
        	{
        	var win = window.open();
            win.document.write(starArray[i]);
            win.print();
            win.close();
        	//rowHTML += "<a href='single-star.html?starName=" + starArray[i] + "'>" + starArray[i] + "</a>";
        	}
       	*/
        var star = resultData[i]["starNames"];
        var starArray = star.split(',');
        var win = window.open();
        win.document.write(starArray.length);
        win.print();
        win.close();
        for(var i = 0; i < starArray.length; i++)
        	{
        	rowHTML += "<a href='single-star.html?starName=" + starArray[i] + "'>" + starArray[i] + "</a>";
        	}
        rowHTML += "</th>";
        //rowHTML += "<th>" + resultData[i]["starNames"] + "</th>";
        rowHTML += "<th>" + resultData[i]["rating"] + "</th>";
        rowHTML += "</tr>";

        // Append the row created to the table body, which will refresh the page
        movieTableBodyElement.append(rowHTML);
    }
}

let title = getParameterByName('title');
let year = getParameterByName('year');
let director = getParameterByName('director');
let starName = getParameterByName('starName');


/*
var win = window.open();
win.document.write(title);
win.document.write(year);
win.document.write(director);
win.document.write(starName);
win.print();
win.close();
*/

//Makes the HTTP GET request and registers on success callback function handleResult
jQuery.ajax({
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "MovieListServlet?title=" +title +"&year=" +year +"&director=" +director +"&starName=" +starName, // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleResult(resultData) // Setting callback function to handle data returned successfully by the SingleStarServlet
});