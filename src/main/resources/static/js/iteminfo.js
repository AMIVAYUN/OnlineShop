function show_explanation() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_evaluation");
    var con4 = document.getElementById("details_QnA");
    if(con1.style.display == "none"){
        con1.style.display = "block";
        con2.style.display = "none";
        con3.style.display = "none";
        con4.style.display = "none";
    } else {
        con1.style.display = "none";
    }
}

function show_buy() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_evaluation");
    var con4 = document.getElementById("details_QnA");
    if(con2.style.display == "none"){
        con1.style.display = "none";
        con2.style.display = "block";
        con3.style.display = "none";
        con4.style.display = "none";
    } else {
        con2.style.display = "none";
    }
}

function show_evaluation() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_evaluation");
    var con4 = document.getElementById("details_QnA");
    if(con3.style.display == "none"){
        con1.style.display = "none";
        con2.style.display = "none";
        con3.style.display = "block";
        con4.style.display = "none";
    } else {
        con3.style.display = "none";
    }
}

function show_QnA() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_evaluation");
    var con4 = document.getElementById("details_QnA");
    if(con4.style.display == "none"){
        con1.style.display = "none";
        con2.style.display = "none";
        con3.style.display = "none";
        con4.style.display = "block";
    } else {
        con4.style.display = "none";
    }
}
