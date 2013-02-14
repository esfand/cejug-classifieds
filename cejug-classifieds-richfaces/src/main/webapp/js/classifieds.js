/*
 * Maxlength for TextAreas.
 */
function ismaxlength(obj, tamanho, e){
	var mlength=tamanho;
	if (obj.getAttribute && obj.value.length>mlength){
		obj.value=obj.value.substring(0,mlength)
	}
}
		
function blockKey(pObj, e, pLength){
	var key;
	var keychar;
	var length = pLength;
	var obj = pObj;
		
	if(obj.getAttribute && obj.value.length < length)
		return true;
	if (window.event)
		key = window.event.keyCode;
	else if (e)
		key = e.which;
	else
		return true;
		
	keychar = String.fromCharCode(key);
	// control keys
	if ((key==null) || (key==0) || (key==8) || (key==9) || (key==13) || (key==27) )
	   return true;
	return false;
}