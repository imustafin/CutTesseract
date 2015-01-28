window.onload = function() {
	set_menu_distance();
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