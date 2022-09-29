const replyManager = (function(){
    const getAll = function(obj, callback){
        console.log("get All...");
        $.getJSON("/replies/"+obj, callback);
    };

    const add    = function(obj, callback){
        $.ajax({
           type : "post",
           url  : "/replies/" + obj.bno,
           data : JSON.stringify(obj),
           dataType : "json",
           beforeSend : function(xhr){
               xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
            },
           contentType : "application/json",
           success : callback
        });
    };

    const update = function(obj, callback){
        $.ajax({
           type : "put",
           url  : "/replies/" + obj.bno,
           data : JSON.stringify(obj),
           dataType : "json",
           contentType : "application/json",
           success : callback
        });
    }

    const remove = function(obj, callback){
        $.ajax({
           type : "delete",
            url: "/replies/" + obj.bno + "/" + obj.rno,
            dataType: "json",
            contentType: "application/json",
            success: callback
        });
    }

    return {
        getAll : getAll,
        add    : add,
        update : update,
        remove : remove
    }
})();