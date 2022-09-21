const replyManager = (function(){
    const getAll = function(obj, callback){
        console.log("get All...");
        $.getJSON("/replies/"+obj, callback);
    };

    const add    = function(obj, callback){
        console.log("add...");
    };

    const update = function(obj, callback){
        console.log("update...");
    }

    const remove = function(obj, callback){
        console.log("remove...");
    }

    return {
        getAll : getAll,
        add    : add,
        update : update,
        remove : remove
    }
})();