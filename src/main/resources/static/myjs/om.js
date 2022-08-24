$(document).ready(function () {
            //   1.hide error sections
            $("#orderModeError").hide();
            $("#orderCodeError").hide();
            $("#orderTypeError").hide();
            $("#orderAcptError").hide();
            $("#orderDescError").hide();

            //   2.define error variables
            var orderModeError = false;
            var orderCodeError = false;
            var orderTypeError = false;
            var orderAcptError = false;
            var orderDescError = false;
            //   3.define validate function
            function validate_orderMode() {
                var val = $("#orderMode").val();
                if (val == '') {
                    $("#orderModeError").show();
                    $("#orderModeError").html("* Please select <b>Mode</b>");
                    $("#orderModeError").css('color', 'red');
                    orderModeError = false;
                } else {
                    $("#orderModeError").hide();
                    orderModeError = true;
                }
                return orderModeError;
            }
            function validate_orderCode() {
                var val = $("#orderCode").val();
                var exp=/^[A-Za-z\0-9\-\s]{4,8}$/;
                if (val =='') {
                    $("#orderCodeError").show();
                    $("#orderCodeError").html("* Please enter <b>Code</b>");
                    $("#orderCodeError").css('color', 'red');
                    orderCodeError = false;
                }else if (!exp.test(val)){
                      $("#orderCodeError").show();
                      $("#orderCodeError").html("<b>Code</b> must be 4-8 uppercase letters");
                      $("#orderCodeError").css('color','red');
                      orderCodeError=false;
                
                }else{
	                  var id=0;  //for register
	                  if($("#id").val()!=undefined){ //for edit
		                 id=$("$id").val();  //if present
	} 
	                   
                     //ajax call start
                     $.ajax({
                          url:'validate',
                          data:{"code":val,"id":id},
                          success:function(resTxt){
                          
                          if(resTxt!=''){            //error,duplicate exist
                          $("#orderCodeError").show();
                          $("#orderCodeError").html(resTxt);
                          $("#orderCodeError").css('color','red');
                          orderCodeError=false;
                          }else{
                          $("#orderCodeError").hide();
                          orderCodeError = true; 
                          }
                    }
                          
                     });           
                 }
                return orderCodeError;
            }
            function validate_orderType() {
                var len = $('[name="orderType"]:checked').length;
                if (len == 0) {
                    $("#orderTypeError").show();
                    $("#orderTypeError").html("*Select one <b>Order Type</b>");
                    $("#orderTypeError").css('color', 'red');
                    orderTypeError = false;
                } else {
                    $("#orderTypeError").hide();
                    orderTypeError = true;
                }
                return orderTypeError;
            }
            function validate_orderAcpt() {
                var len = $('[name="orderAcpt"]:checked').length;
                if (len == 0) {
                    $("#orderAcptError").show();
                    $("#orderAcptError").html("*Select one <b>Order Accept</b>");
                    $("#orderAcptError").css('color', 'red');
                    orderAcptError = false;
                } else {
                    $("#orderAcptError").hide();
                    orderAcptError = true;
                }
                return orderAcptError;
            }
            function validate_orderDesc() {
                var val = $("#orderDesc").val();
                if (val == '') {
                    $("#orderDescError").show();
                    $("#orderDescError").html("* Please enter <b>Description</b>");
                    $("#orderDescError").css('color', 'red');
                    orderDescError = false;
                } else {
                    $("#orderDescError").hide();
                    orderDescError = true;
                }
                return orderDescError;
            }
            // 4.link with action event
            $("#orderMode").change(function () {
                validate_orderMode();
            })
            $("#orderCode").keyup(function () {
               $(this).val($(this).val().toUpperCase());
                validate_orderCode();
            })
            $('[name="orderType"]').change(function () {
                validate_orderType();
            })
            $('[name="orderAcpt"]').change(function () {
                validate_orderAcpt();
            })
            $("#orderDesc").keyup(function () {
                validate_orderDesc();
            })



            //5.on click form submit
            $("#shipmentTypeRegister").submit(function () {
                validate_orderType();
                validate_orderCode();
                validate_orderDesc();
                validate_orderAcpt();
                validate_orderMode();


                if (orderCodeError && orderDescError &&
                    orderAcptError && orderModeError && orderTypeError)
                    return true;
                else return false;
            })

        });
