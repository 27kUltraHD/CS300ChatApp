// small helper for selecting element by id
let id = id => document.getElementById(id);

id("modal-login").addEventListener("click", show);
id("close").addEventListener("click", hide);
id("cancelbtn").addEventListener("click", hide);

function show(){
    id("id01").style.display="block";
}

function hide(){
    id("id01").style.display="none";
}

