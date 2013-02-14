/* AJAX Star Rating : v1.0.3 : 2008/05/06 */
/* http://www.nofunc.com/AJAX_Star_Rating/ */

function Aa(v,o) { return((typeof(o)=='object'?o:document).getElementById(v)); }
function AaS(o) { return((typeof(o)=='object'?o:Aa(o)).style); }
function agent(v) { return(Math.max(navigator.userAgent.toLowerCase().indexOf(v),0)); }
function abPos(o) { var o=(typeof(o)=='object'?o:Aa(o)), z={X:0,Y:0}; while(o!=null) { z.X+=o.offsetLeft; z.Y+=o.offsetTop; o=o.offsetParent; }; return(z); }
function XY(e,v) { var o=agent('msie')?{'X':event.clientX+document.body.scrollLeft,'Y':event.clientY+document.body.scrollTop}:{'X':e.pageX,'Y':e.pageY}; return(v?o[v]:o); }

star={};

star.mouse=function(e,o) { if(star.stop || isNaN(star.stop)) { star.stop=0;

	document.onmousemove=function(e) { var n=star.num;
	
		var p=abPos(Aa('star'+n)), x=XY(e), oX=x.X-p.X, oY=x.Y-p.Y; star.num=o.id.substr(4);

		if(oX<1 || oX>84 || oY<0 || oY>19) { star.stop=1; star.revert(); }
		
		else {

			AaS('starCur'+n).width=oX+'px';
			AaS('starUser'+n).color='#111';
			Aa('starUser'+n).innerHTML=Math.round(oX/84*100)+'%';
		}
	};
} };

star.update=function(e,o) { var n=star.num, v=parseInt(Aa('starUser'+n).innerHTML);

	n=o.id.substr(4); Aa('starCur'+n).title=v;

	req=new XMLHttpRequest(); 
	req.open('GET','/cejug-classifieds-richfaces/pages/starRating/starRating.faces?vote='+(v/100),true);
	req.send(null);    

};

star.revert=function() { var n=star.num, v=parseInt(Aa('starCur'+n).title);

	AaS('starCur'+n).width=Math.round(v*84/100)+'px';
	Aa('starUser'+n).innerHTML=(v>0?Math.round(v)+'%':'');
	Aa('starUser'+n).style.color='#888';
	
	document.onmousemove='';

};

star.num=0;
