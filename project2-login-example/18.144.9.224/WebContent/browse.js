
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



function handleResult(resultData) {
    console.log("handleResult: populating star info from resultData");

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
        rowHTML += "<th>";
        var genre = resultData[i]["genreNames"];
        var genreArray = genre.split(',');
        for(var z = 0; z < genreArray.length; z++)
        	{
        	if(z == (genreArray.length-1))
        		{
        		rowHTML += genreArray[z];
        		break;
        		}	
        	rowHTML += genreArray[z] + ", ";
        	}
        rowHTML += "</th>";
        rowHTML += "<th>";
        var star = resultData[i]["starNames"];
        var starArray = star.split(',');
        for(var x = 0; x < starArray.length; x++)
        	{
        	if(x == (starArray.length-1))
        		{
        		rowHTML += "<a href='single-star.html?starName=" + starArray[x] + "'>" + starArray[x] + "</a>";
        		break;
        		}	
        	rowHTML += "<a href='single-star.html?starName=" + starArray[x] + "'>" + starArray[x] + "</a>, ";
        	}
        rowHTML += "</th>";
        rowHTML += "<th>" + resultData[i]["rating"] + "</th>";
        rowHTML += "</tr>";

        // Append the row created to the table body, which will refresh the page
        movieTableBodyElement.append(rowHTML);
    }
}


let method = getParameterByName('by');
console.log(method);
let title = getParameterByName('title');
let year = getParameterByName('year');
let director = getParameterByName('director');
let starName = getParameterByName('starName');
let genre = getParameterByName('genre');
let genrename = getParameterByName('genreid');
let initial = getParameterByName('init');



// Makes the HTTP GET request and registers on success callback function handleResult
jQuery.ajax({
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "api/browse?by=" + method + "&genreid=" + genrename + "&init=" + initial, // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleResult(resultData) // Setting callback function to handle data returned successfully by the SingleStarServlet
});