(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-30773e12"],{"0c4b":function(t,a,e){"use strict";e.r(a);var s=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"contain"},[e("el-radio-group",{staticStyle:{"margin-bottom":"20px"},on:{change:function(a){return t.change(0)}},model:{value:t.radio,callback:function(a){t.radio=a},expression:"radio"}},[e("el-radio",{attrs:{label:0}},[t._v("ALL")]),e("el-radio",{attrs:{label:1}},[t._v("未处理")]),e("el-radio",{attrs:{label:2}},[t._v("已处理")])],1),e("el-table",{attrs:{data:t.tableData,"header-cell-style":{background:"#eef1f6",color:"#606266"},"row-class-name":t.tableRowClassName}},[e("el-table-column",{attrs:{label:"feedbackid",width:"100",prop:"feedbackid"}}),e("el-table-column",{attrs:{label:"userid",width:"100",prop:"userid"}}),e("el-table-column",{attrs:{label:"content",width:"700",prop:"content","show-overflow-tooltip":!0}}),e("el-table-column",{attrs:{label:"processing",width:"200"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("div",{staticClass:"icon1",on:{click:function(e){t.dialogVisible=!0,t.currentpro(a.row)}}},[0===a.row.isRead?e("div",[e("svg-icon",{staticClass:"icon2",attrs:{"icon-class":"daichuli"}}),t._v(" 未处理 ")],1):2===a.row.isRead?e("div",[e("svg-icon",{staticClass:"icon2",attrs:{"icon-class":"yichuli"}}),t._v(" 已处理 ")],1):t._e()])]}}])})],1),e("el-dialog",{attrs:{title:"提示",visible:t.dialogVisible,width:"30%"},on:{"update:visible":function(a){t.dialogVisible=a}}},[e("span",[t._v("您确定要修改为"+t._s(t.nextmessage)+"吗？")]),e("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[e("el-button",{on:{click:function(a){t.dialogVisible=!1}}},[t._v("取 消")]),e("el-button",{attrs:{type:"primary"},on:{click:function(a){t.dialogVisible=!1,t.updataIsRead()}}},[t._v("确 定")])],1)]),e("el-pagination",{attrs:{background:"",layout:"prev, pager, next","page-size":t.pageSize,total:t.total},on:{"current-change":t.change}})],1)},i=[],o={name:"Feedback",data:function(){return{pageSize:6,total:10,tablenum:0,tableData:[],radio:0,params:"",updata_params:"",dialogVisible:!1,currentprocess:0,currentfeedbackid:0,nextmessage:""}},created:function(){var t=this;this.$axios.get("http://localhost:8080/feedback/findAll/0/8").then((function(a){t.tableData=a.data.content,t.total=a.data.totalElements}))},methods:{currentpro:function(t){this.currentprocess=t.isRead,this.currentfeedbackid=t.feedbackid,0===t.isRead?this.nextmessage="已处理":this.nextmessage="未处理"},updataIsRead:function(){0===this.currentprocess?this.updata_params="processed/":this.updata_params="processing/",this.$axios.get("http://localhost:8080/feedback/"+this.updata_params+this.currentfeedbackid).then((function(t){})),location.reload()},tableRowClassName:function(t){var a=t.row;return 0===a.isRead?"processing":2===a.isRead?"processed":""},change:function(t){var a=this;0===this.radio?this.params="All/":1===this.radio?this.params="NotOperate/":this.params="Operate/",this.$axios.get("http://localhost:8080/feedback/find"+this.params+(t-1)+"/8").then((function(t){console.log(t),a.tableData=t.data.content,a.total=t.data.totalElements}))}}},n=o,l=(e("1339"),e("4ac2")),r=Object(l["a"])(n,s,i,!1,null,null,null);a["default"]=r.exports},1339:function(t,a,e){"use strict";e("89d5")},"89d5":function(t,a,e){}}]);