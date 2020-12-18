 
 

function simpleValidation($input){
    var valid = true,
        value = $input.val();

        if(value.length == 0){
            valid = false;
            $input.closest('.form-group').find('.help-block').show();
        }else{
            $input.closest('.form-group').find('.help-block').hide();
        }

    return valid;
}


 
