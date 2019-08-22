/// <reference path="utils.js" />

/**
* 模块名：共通脚本
* 程序名: 通用方法common.js
**/
var com = {};

/**
 * 格式化时间
 */
com.formatTime = function (value) {
	if(utils.isEmpty(value)){
		return;
	}else if((''+value).indexOf('-')>-1){
		return utils.formatDate(value, "yyyy-MM-dd hh:mm:ss");
	}else{
		return utils.formatDate(new Date(parseFloat(value)), "yyyy-MM-dd hh:mm:ss");
	}  
};

/**
 * 格式化金额，三位一个逗号，保留2位小数
 */
com.formatMoney = function (value) {
    var sign = value < 0 ? '-' : '';
    return sign + utils.formatNumber(Math.abs(value), '#,##0.00');
};

/**
 * 消息弹出框
 * info、error、warning、confirm
 */
com.message = function (type, message, callback) {
	var isNotity = false;
	if(top.$('#notity').jnotifyAddMessage!=undefined){
		isNotity = true;
	}
	$.parser.onComplete = $.noop;
	var win;
    switch (type) {       
    	case "success":
    		if(isNotity)
    			top.$('#notity').jnotifyAddMessage({ text: message, permanent: false, type: 'success' });
			else{
				win = $.messager.alert('成功', message, 'info'); 
				$.parser.parse(win);
			}
			break;
        case "error":
        	if(isNotity)
        		top.$('#notity').jnotifyAddMessage({ text: message, permanent: false, type: 'error' });
            else{
            	win = $.messager.alert('错误', message, 'error');
            	$.parser.parse(win);
			}
            break;
        case "warning": 
        	if(isNotity)
            	top.$('#notity').jnotifyAddMessage({ text: message, permanent: false, type: 'warning' });
            else{
            	win = $.messager.alert('警告', message, 'warning'); 
            	$.parser.parse(win);
            }
            break;   
        case "confirm":
			win = $.messager.confirm('确认', message, callback);
			break;
    }
    if (callback) callback();
    return null;
};

/**
 * 如果条件成立，弹出消息框
 */
com.messageif = function (condition, type, message, callback) {
    if (condition) 
        com.message(type, message, callback);
};

/**
 * ajax异步提交，提交json，返回json
 */
com.ajax = function (options) {
    options = $.extend({
        showLoading:true
    }, options);

    var ajaxDefaults = {
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        async:false,
        error: function (e) {
            var msg = e.responseText;
            var ret = msg.match(/^{\"Message\":\"(.*)\",\"ExceptionMessage\":\"(.*)\",\"ExceptionType\":.*/);
            if (ret != null) {
                msg = (ret[1] + ret[2]).replace(/\\"/g, '"').replace(/\\r\\n/g, '<br/>').replace(/dbo\./g, '');
            }
            else {
                try{msg = $(msg).text()||msg;}
                catch(ex){}
            }
           
            com.message('error', msg);
        }
    };

    if (options.showLoading) {
        ajaxDefaults.beforeSend = $.showLoading;
        ajaxDefaults.complete = $.hideLoading;
    }

    $.ajax($.extend(ajaxDefaults, options));
};

/**
 * 取得jquery的$对象，可是当前页面的或者父页面的
 */
com.query = function (element) {
    var query = $;
    if ($(document).find(element).length == 0 && element!=document) {
        if ($(parent.document).find(element)) {
            query = parent.$;
        }
    }
    return query;
};

/**
 * 用来提交前对表单进行校验
 */
com.formValidate = function (context) {
    context = context || document;
    var query = com.query(context);
    if (query.fn.validatebox) {
        var box = query(".validatebox-text", context);
        if (box.length) {
            box.validatebox("validate");
            box.trigger("focus");
            box.trigger("blur");
            var valid = $(".validatebox-invalid:first", context).focus();
            return valid.length == 0;
        }
    }
    return true;
};

/**
 * 判断两个表单对象是否发生改变，返回obj1中不同的键值对
 */
com.formChanges = function (obj1,obj2,reserve) {
    obj1 = ko.toJS(obj1) || {};
    obj2 = ko.toJS(obj2) || {};
    reserve = reserve || [];
    var result = utils.diffrence(obj1, obj2, reserve, ['__ko_mapping__']);

    var length = 0;
    for (var k in result) length++;
    result._changed = length > reserve.length;

    return result;
};

/**
 * 编辑grid的通用viewModel绑定
 */
com.editGridViewModel = function (grid) {
    var self = this;
    this.begin = function (index, row) {
        if (index== undefined || typeof index === 'object') {
            row = grid.datagrid('getSelected');
            index = grid.datagrid('getRowIndex', row);
        }
        self.editIndex = self.ended() ? index : self.editIndex;
        grid.datagrid('selectRow', self.editIndex).datagrid('beginEdit', self.editIndex);
    };
    this.ended = function () {
        if (self.editIndex == undefined) return true;
        if (grid.datagrid('validateRow', self.editIndex)) {
            grid.datagrid('endEdit', self.editIndex);
            self.editIndex = undefined;
            return true;
        }
        grid.datagrid('selectRow', self.editIndex);
        return false;
    };
    this.addnew = function (rowData) {
        if (self.ended()) {
            if (Object.prototype.toString.call(rowData) != '[object Object]') rowData = {};
            rowData = $.extend({_isnew:true},rowData);
            grid.datagrid('appendRow', rowData);
            self.editIndex = grid.datagrid('getRows').length - 1;
            grid.datagrid('selectRow', self.editIndex);
            self.begin(self.editIndex, rowData);
        }
    };
    this.deleterow = function () {
        var selectRow = grid.datagrid('getSelected');
        if (selectRow) {
            var selectIndex = grid.datagrid('getRowIndex', selectRow);
            if (selectIndex == self.editIndex) {
                grid.datagrid('cancelEdit', self.editIndex);
                self.editIndex = undefined;
            }
            grid.datagrid('deleteRow', selectIndex);
        }
    };
    this.reject = function () {
        grid.datagrid('rejectChanges');
    };
    this.accept = function () {
        grid.datagrid('acceptChanges');
        var rows = grid.datagrid('getRows');
        for (var i in rows) delete rows[i]._isnew;
    };
    this.getChanges = function (include, ignore) {
        if (!include) include = [], ignore = true;
        var deleted = utils.filterProperties(grid.datagrid('getChanges', "deleted"), include, ignore),
            updated = utils.filterProperties(grid.datagrid('getChanges', "updated"), include, ignore),
            inserted = utils.filterProperties(grid.datagrid('getChanges', "inserted"), include, ignore);

        var changes = { deleted: deleted, inserted: utils.minusArray(inserted, deleted), updated: utils.minusArray(updated, deleted) };
        changes._changed = (changes.deleted.length + changes.updated.length + changes.inserted.length)>0;

        return changes;
    };
    this.isChangedAndValid = function () {
        return self.ended() && self.getChanges()._changed;
    };
};

/**
 * 弹出指定url的对话框，并且绑定viewModel
 */
com.dialog = function (opts) {
    var query = top.$ || $, fnClose = opts.onClose;
    opts = query.extend({
        title: 'My Dialog',
        width: 400,
        height: 220,
        closed: false,
        cache: false,
        modal: true,
        html: '',
        url: '',
        viewModel: query.noop
    }, opts);

    opts.onClose = function () {
        if (query.isFunction(fnClose)) fnClose();
        query(this).dialog('destroy');
    };
     
    if (query.isFunction(opts.html))
        opts.html = utils.functionComment(opts.html);
    else if (/^\#.*\-template$/.test(opts.html))
        opts.html = $(opts.html).html();
 
    var win = query('<div></div>').appendTo('body').html(opts.html);
    if (opts.url) 
        query.ajax({async: false,url: opts.url,success: function (d) { win.empty().html(d); }});

    win.dialog(opts);
    query.parser.onComplete = function () {
        if ("undefined" === typeof ko)
            opts.viewModel(win);
        else
            ko.applyBindings(new opts.viewModel(win), win[0]);

        query.parser.onComplete = query.noop;
    };
    query.parser.parse(win);
    return win;
};

/**
 * 设置input、combo为只读状态，一般用在grid中
 */
com.readOnlyHandler = function (type) {
    _readOnlyHandles = {};
    _readOnlyHandles.defaults = _readOnlyHandles.input = function (obj, b) {
        b ? obj.addClass("readonly").attr("readonly", true) : obj.removeClass("readonly").removeAttr("readonly");
    };
    _readOnlyHandles.combo = function (obj, b) {
        var combo = obj.data("combo").combo;
        _readOnlyHandles.defaults(combo.find(".combo-text"), b);
        if (b) {
            combo.unbind(".combo");
            combo.find(".combo-arrow,.combo-text").unbind(".combo");
            obj.data("combo").panel.unbind(".combo");
        }
    };
    return _readOnlyHandles[type || "defaults"];
};

/**
 * 取得访问工程的基础url路径
 */
com.getRootPath = function getRootPath(){
	 var strFullPath=window.document.location.href;
	 var strPath=window.document.location.pathname;
	 var pos=strFullPath.indexOf(strPath);
	 var prePath=strFullPath.substring(0,pos);
	 var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	 return(prePath+postPath);
}; 
var rootPath = com.getRootPath();

/**
 * 判断浏览器类型
 */
com.isIE = navigator.userAgent.toUpperCase().indexOf("MSIE")!=-1?true:false;
com.isFirefox = navigator.userAgent.toUpperCase().indexOf("FIREFOX")!=-1?true:false;

/**
 * 重新加载单个css文件
 */
com.loadCss = function (url, doc, reload) {
    var links = doc.getElementsByTagName("link");
    for (var i = 0; i < links.length; i++)
        if (links[i].href.indexOf(url) > -1) {
            if (reload)
                links[i].parentNode.removeChild(links[i])
            else 
                return;
        }
    var container = doc.getElementsByTagName("head")[0];
    var css = doc.createElement("link");
    css.rel = "stylesheet";
    css.type = "text/css";
    css.media = "screen";
    css.href = url;
    container.appendChild(css);
};

/**
 * 向前合并对象中没有属性或者值为空的属性，返回新对象
 */
com.extend = function(o, n){
	var t = {};
	$.extend(t, o);
	for (var p in n){
		if(!t.hasOwnProperty(p) || t[p]==null){
			t[p]=n[p];
		}
   	}   
   	return t; 
}

/**
 * 弹出选项页
 */
com.openTab = function (subtitle, url) {
	if(parent.wrapper != undefined &&
		parent.wrapper.addTab != undefined){
    	parent.wrapper.addTab.apply(this,arguments);
    }else{
    	window.location.href = url;
    }
}