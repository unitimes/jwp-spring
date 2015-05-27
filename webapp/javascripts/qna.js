	window.addEventListener('load', function() {
		registerEvents();
	});

	function registerEvents() {
		var elWriteForm = document.querySelector('.answerWrite input[type=submit]');
		var elComments = document.querySelector('#comments');
		var elQuestionBtnContainer = document.querySelector('.buttons');

		elWriteForm.addEventListener('click', writeAnswer, false);
		elComments.addEventListener('click', deleteAnswer, false);
		elQuestionBtnContainer.addEventListener('click', processClick, false);
	}
	
	function processClick(e) {
		e.preventDefault();
		
		if (e.target.href.includes("delete")) {
			var url = e.target.href;
			var request = new XMLHttpRequest();
			request.open("GET", url, true);

			request.onreadystatechange = function() {
				if (request.readyState == 4 && request.status == 200) {
					var result = JSON.parse(request.responseText);
					if (!result.status) {
						alert(result.errorMessage);
						return;
					}
					location.href = "/questions";
				}
			}
			request.send();
			return;
		}
		location.href = e.target.href;
	}

	function deleteAnswer(e) {
		e.preventDefault();
		if (e.target.nodeName !== 'A') {
			return;
		}

		var ids = e.target.getAttribute("data-ids").split('_');
		var url = "/api/questions/" + ids[0] + "/answers/" + ids[1];

		var request = new XMLHttpRequest();
		request.open("DELETE", url, true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				var result = JSON.parse(request.responseText);
				if (result.status) {
					// delete current NODE.
					var elCurrent = e.target.parentElement.parentElement;
					elCurrent.parentNode.removeChild(elCurrent);
				}
			}
		}
		request.send();
	}

	function writeAnswer(e) {
		e.preventDefault();

		var answerForm = e.currentTarget.form;
		var url = "/api/questions/" + answerForm[0].value + "/answers";
		var params = "writer=" + answerForm[1].value + "&contents=" + answerForm[2].value;

		var request = new XMLHttpRequest();
		request.open("POST", url, true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				var result = JSON.parse(request.responseText);
				if (!result.status) {
					alert(result.errorMessage);
				}
				location.reload(true)
			}
		}

		request.send(params);
	}

