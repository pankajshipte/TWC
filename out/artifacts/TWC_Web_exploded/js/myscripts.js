	var rem;
	var limit = 1;
	var angle = 90;
	var x = 0;
	var filePath = "";
	var counter = setInterval(getNext, 4000);

	function isNumberKey(evt){
		var charCode = (evt.which) ? evt.which : event.keyCode
		if (charCode > 31 && (charCode < 48 || charCode > 57))
		  return false;
		return true;
	}

	function setPageSVG(val, fileName, orient) {
		limit = val;
		filePath = fileName;
		angle = orient;
		wordScale=d3.scale.linear().domain([1,100,1000,10000]).range([10,20,40,80]).clamp(true);
		wordColor=d3.scale.linear().domain([10,20,40,80]).range(["blue","green","orange","red"]);

		viz = d3.select("#image").append("svg")
			.attr("width", 600)
			.attr("height", 400)
			.attr("id", "svg");
	};

	function getNext() {
		//alert("interval = "+limit+" x="+x);
		if(x < limit-1){
			x++;
		}else{
			x = 0;
		}
		window.getSVG();
	}

	function getNextBtn() {
		if(x < limit-1){
			x++;
		}else{
			x = 0;
		}
		viz = d3.select("#svg");
		viz.selectAll("*").remove();
		window.getSVGBtn();
	}

	function getPrevBtn() {
		if(x > 0){
			x--;
		}else{
			x = limit-1;
		}
		viz = d3.select("#svg");
		viz.selectAll("*").remove();
		window.getSVGBtn();
	}

	function stopTimer () {
		viz = d3.select("#svg");
				viz.selectAll("*").transition()
				.duration(2000)
    			.delay(500)
    			.style("opacity", 0)
				.remove();
	}
	
	function hideButtons(){
		document.getElementById("prev").style.visibility = "hidden";
		document.getElementById("next").style.visibility = "hidden";
		document.getElementById("resume").style.visibility = "hidden";
		document.getElementById("stop").style.visibility = "visible";
	}

	function stopAnimation () {
		clearTimeout(rem);
		clearInterval(counter);
		document.getElementById("prev").style.visibility = "visible";
		document.getElementById("next").style.visibility = "visible";
		document.getElementById("resume").style.visibility = "visible";
		document.getElementById("stop").style.visibility = "hidden";
	}

	function resumeAnimation () {
		x--;
		window.getNext();
		counter = setInterval(getNext, 4000);
	}
	function getSVG() {

		//d3.json("topic.json", function(topic) {
		// alert(x);
		/*alert(filePath+x+".csv#"+Math.floor(Math.random() * 1000));*/
		d3.csv(filePath+x+".csv" + '#' + Math.floor(Math.random() * 1000), function(topic) {
			/*alert(filePath+x+".csv");*/
			d3.layout.cloud().size([600, 400])
			//      .words([{"text":"test","size":wordScale(.01)},{"text":"bad","size":wordScale(1)}])
			.words(topic)
			.padding(3)
			.rotate(function() { return ~~(Math.random()*2) * angle;}) // 0 or 90deg
			.fontSize(function(d) { return wordScale(d.size); })
			.font('font-family', function(d) { return d.font; })
			.on("end", draw)
			.start();

			function draw(words) {

				rem = setTimeout(stopTimer,2000);
				// .duration(2000)
    // 			.delay(function(d, i) { return i * 20; })
				// .remove();

				viz.append("g")
				.attr("class", "animate")
				.attr("transform", "translate(295,205)")
				.selectAll("text")
				.data(words)
				.enter().append("text")
				.style('font-size', function(d) { return d.size + 'px'; })
				.style('font-family', function(d) { return d.font; })
				.style('fill', function(d, i) { return wordColor(i); })
				.attr('text-anchor', 'middle')
				.attr('transform', function(d) {
				return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')';
				})
				.transition()
				.duration(2000)
    			.delay(function(d, i) { return i * 20; })
				.text(function(d) { return d.text; });

				viz = d3.select("#time");
				viz.selectAll("*").remove();

				viz.append("h1")
				.data([topic[0]])
				.text(function(d) { return "Interval: " + x; });

				//  alert(x);
				//  d3.select("#svg"+x).append("svg:text").text("Topic " + x);  
				//    viz.enter().append("svg:text").text("Topic " + x);
			}	// End of Draw

		})
	};

	function getSVGBtn() {

		d3.csv(filePath+x+".csv" + '?' + Math.floor(Math.random() * 1000), function(topic) {

			d3.layout.cloud().size([600, 400])
			.words(topic)
			.padding(3)
			.rotate(function() { return ~~(Math.random()*2) * angle;}) // 0 or 90deg
			.fontSize(function(d) { return wordScale(d.size); })
			.font('font-family', function(d) { return d.font; })
			.on("end", draw)
			.start();

			function draw(words) {

				viz.append("g")
				.attr("class", "animate")
				.attr("transform", "translate(295,205)")
				.selectAll("text")
				.data(words)
				.enter().append("text")
				.style('font-size', function(d) { return d.size + 'px'; })
				.style('font-family', function(d) { return d.font; })
				.style('fill', function(d, i) { return wordColor(i); })
				.attr('text-anchor', 'middle')
				.attr('transform', function(d) {
					return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')';
				})
				/*.transition()
				.duration(2000)
    			.delay(function(d, i) { return i * 20; })*/
				.text(function(d) { return d.text; });

				viz = d3.select("#time");
				viz.selectAll("*").remove();

				viz.append("h1")
				.data([topic[0]])
				.text(function(d) { return "Interval: " + x; });
			}	// End of Draw

		})
	};