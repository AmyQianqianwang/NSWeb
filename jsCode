/**
 * ctrl+f bind to search-input
 */
window.onload=function () { 
    HotKeyHandler.Init(); 
} 
var HotKeyHandler={ 
    currentMainKey:null, 
    currentValueKey:null, 
    Init:function () { 
        HotKeyHandler.Register(0,"F",function () {
            document.getElementById('search-input').focus();
        });   	
    }, 
    Register:function (tag,value,func) { 
        var leftCommandKeyCode=""; 
        var rightCommandKeyCode=""; 
        var userAgent = navigator.userAgent;
        var platform  = navigator.platform;
            switch (tag) { 
                case 0: 
                    if (platform.match('Mac') && (userAgent.match('Safari') || userAgent.match('Chrome'))) {
                        leftCommandKeyCode  = 91;
                        rightCommandKeyCode = 93;
                    } else if (platform.match('Mac') && userAgent.match('Opera')) {
                        leftCommandKeyCode  = 17;
                        rightCommandKeyCode = 17;
                    } else if (platform.match('Mac') && userAgent.match('Firefox')) {
                        leftCommandKeyCode  = 224;
                        rightCommandKeyCode = 224;
                    } else {
                        leftCommandKeyCode  = 17;
                        rightCommandKeyCode = 17;
                    }
                    break; 
            } 
        document.onkeyup=function (e) { 
            HotKeyHandler.currentMainKey=null; 
        }  
        document.onkeydown=function (event) { 
            var keyCode= event.keyCode ; 
            var keyValue = String.fromCharCode(event.keyCode); 
            if (HotKeyHandler.currentMainKey!=null) { 
                if (keyValue==value){  
                    HotKeyHandler.currentMainKey=null; 
                    if (func!=null) {
                      func(); 
                    }
                } 
            } 
            if (keyCode==leftCommandKeyCode||keyCode==rightCommandKeyCode) {
                HotKeyHandler.currentMainKey=keyCode; 
            }
        }        
    } 
}; 

key('⌘+f, ctrl+f', function(event, handler){
    return false;
});

$(document).ready(function(){  
    $("input").focus(function(){  
        document.querySelector("#search-input").addEventListener('keydown', function(e) {      
            var keyCode = e.keyCode || e.which || e.charCode;
            var ctrlKey = e.ctrlKey || e.metaKey;
                if(ctrlKey && keyCode == 70) {
                    e.preventDefault();
                }
        });  
    });  
    $("input").blur(function(){              
    });  
});  



//waitingDialog.js
/**
 * Module for displaying "Waiting for..." dialog using Bootstrap
 *
 * @author Eugene Maslovich <ehpc@em42.ru>
 */

var waitingDialog = (function ($) {

    // Creating modal dialog's DOM
	var $dialog = $(
		'<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
		'<div class="modal-dialog modal-m">' +
		'<div class="modal-content">' +
			'<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
			'<div class="modal-body">' +
				'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
			'</div>' +
		'</div></div></div>');

	return {
		/**
		 * Opens our dialog
		 * @param message Custom message
		 * @param options Custom options:
		 * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
		 * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
		 */
		show: function (message, options) {
			// Assigning defaults
			var settings = $.extend({
				dialogSize: 'm',
				progressType: ''
			}, options);
			if (typeof message === 'undefined') {
				message = 'Loading';
			}
			if (typeof options === 'undefined') {
				options = {};
			}
			// Configuring dialog
			$dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
			$dialog.find('.progress-bar').attr('class', 'progress-bar');
			if (settings.progressType) {
				$dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
			}
			$dialog.find('h3').text(message);
			// Opening dialog
			$dialog.modal();
		},
		/**
		 * Closes dialog
		 */
		hide: function () {
			$dialog.modal('hide');
		}
	}

})(jQuery);
