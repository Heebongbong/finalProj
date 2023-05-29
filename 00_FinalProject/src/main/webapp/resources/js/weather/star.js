/**
 * 
 */
 
 $(document).ready(function(){
 
 	setInterval(changePicture,100);
 	
 });

function changePicture(){
	if(imgArray.length > 0){
		$('#gifEffectImg').attr('src', imgArray[imgIndex]);
		imgIndex++;
		if(imgIndex >= imgArray.length){ 
			imgIndex = 0;
		}
	}
}