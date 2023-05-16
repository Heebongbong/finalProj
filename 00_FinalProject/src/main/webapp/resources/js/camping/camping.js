/**
 * 
 */
 
 function locSearch() {
	let nums = $('#nums').val();
	location.href='${ctxPath}/camping/content?num='+nums;
}