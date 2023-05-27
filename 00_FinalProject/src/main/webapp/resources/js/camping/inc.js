/**
 * 
 */
 
 function locSearch() {
		let locs = $('#locs').val();
		location.href='${ctxPath}/camping/camping?keyword='+locs;
	}