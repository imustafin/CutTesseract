window.onload = function() {
	set_menu_distance();
	//leaf_through_gallety("right");
};

function show_hide(id){
	var item = document.getElementById(id);
	if (item.style.display == 'none') {item.style.display = 'block';}
	else item.style.display = 'none';
}

function set_menu_distance(){
	var height = document.getElementById("header").offsetHeight;
	var div = document.getElementsByClassName("menu");
	div = div[0];
	div.style.top = height + 2 + "px";
}

function leaf_through_gallety(where){
	
	var images = document.getElementById("gallery").childNodes;
	var child = [];
	for(var i = 0; i < images.length; i++){
		if(images[i].tagName == 'A'){
			child.push(images[i]);
		}
	}
	if(where == "right"){
		for(var i = 0; i < child.length - 1; i++){
			child[i].href = child[i+1].href;
			var img = child[i].childNodes[0];
			img.src = child[i+1].href;
			//console.log(img);
		}
	}
}

/*
var href = images[i].href;
var filename = href.substring(href.lastIndexOf('/') + 1, href.lastIndexOf('.'));
var fileformat = href.substring(href.lastIndexOf('.') + 1);
var url = href.substring(0, href.lastIndexOf('/'));
var newname = parseInt(filename.substring(filename.length-1, filename.length))+1;
console.log(newname);
*/