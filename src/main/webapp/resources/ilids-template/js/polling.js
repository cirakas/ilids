
var allow = true;
var startUrl;
var pollUrl;
var phase1Value;
var phase2Value;
var phase3Value;


function Poll() {

	this.start = function start(start, poll) {
		startUrl = start;
		pollUrl = poll;
		
		if (request) {
			request.abort(); // abort any pending request
		}

		// fire off the request to MatchUpdateController
		var request = $.ajax({
			url : startUrl,
			type : "get"
		});

		// This is jQuery 1.8+
		// callback handler that will be called on success
		request.done(function(reply) {
			setInterval(function() {
				if (allow === true) {
					allow = false;
					getUpdate();
				}
			}, 500000);
		});
		
		// callback handler that will be called on failure
		request.fail(function(jqXHR, textStatus, errorThrown) {
			// log the error to the console
			console.log("Start - the following error occured: " + textStatus, errorThrown);
		});
		
		
	};
	
	function getUpdate() {
		if (request) {
			request.abort();	// abort any pending request
		}
		
		// fire off the request to MatchUpdateController
		var request = $.ajax({
			url : pollUrl,
			type : "get"
		});

		// This is jQuery 1.8+
		// callback handler that will be called on success
		request.done(function(pollData) {
       		getUpdate(pollData);
      
		});
		
		function getUpdate(pollData) {
                if(pollData.alertListValue && pollData.alertListValue!=='undefined'){
                    if(pollData.alertListValue!=='null'){
                          generateNotification(pollData.alertListValue);
                    }
                }  
                  
                   
         phase1Value='<div id="phase1PowerFactor"><p class="announcement-text">Power factor Phase 1: '+ Number(pollData.phase1Value)+'</p></div>';
         phase2Value='<div id="phase2PowerFactor"><p class="announcement-text">Power factor Phase 2: '+ Number(pollData.phase2Value)+'</p></div>';
         phase3Value='<div id="phase3PowerFactor"><p class="announcement-text">Power factor Phase 3: '+ Number(pollData.phase3Value)+'</p></div>';
         if(phase1Value){
          $(phase1Value).replaceAll('#phase1PowerFactor');
            $('#phase1PowerFactorPanel').removeClass('power-factor-success');
            $('#phase1PowerFactorPanel').removeClass('power-factor-warning');
           if(Number(pollData.phase1Value)<.9 || Number(pollData.phase1Value)===42){
            $('#phase1PowerFactorPanel').addClass('power-factor-warning');
          }else{
            $('#phase1PowerFactorPanel').addClass('power-factor-success');
          }
        }
          if(phase2Value){
          $(phase2Value).replaceAll('#phase2PowerFactor');
            $('#phase2PowerFactorPanel').removeClass('power-factor-success');
            $('#phase2PowerFactorPanel').removeClass('power-factor-warning');
          if(Number(pollData.phase1Value)<.9 || Number(pollData.phase1Value)===42){
            $('#phase2PowerFactorPanel').addClass('power-factor-warning');
          }else{
            $('#phase2PowerFactorPanel').addClass('power-factor-success');
          }
         
           
        }
          if(phase3Value){
           $(phase3Value).replaceAll('#phase3PowerFactor');
             $('#phase3PowerFactorPanel').removeClass('power-factor-success');
            $('#phase3PowerFactorPanel').removeClass('power-factor-warning');
            if(Number(pollData.phase1Value)<.9 || Number(pollData.phase1Value)===42){
            $('#phase3PowerFactorPanel').addClass('power-factor-warning');
          }else{
            $('#phase3PowerFactorPanel').addClass('power-factor-success');
          }
         }
		};
		

		// callback handler that will be called on failure
		request.fail(function(jqXHR, textStatus, errorThrown) {
			// log the error to the console
			console.log("Polling - the following error occured: " + textStatus, errorThrown);
		});

		// callback handler that will be called regardless if the request failed or succeeded
		request.always(function() {
			allow = true;
		});
	};	
        
    function printBr(element, index, array) {
        generateNotification(element);
};
        
        
};