function toggleModal(modalId) {
  let modal = document.getElementById(modalId);
  modal.style.display = (modal.style.display === "none" ? "block" : "none");
  
  if(modal.style.display == 'block') {
  	document.getElementById("btn").innerText = "닫기";
  }else {
  	document.getElementById("btn").innerText = "삭제";
  }
}