(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4d070026"],{1:function(e,t){},2:function(e,t){},"22bd":function(e,t,a){},3:function(e,t){},"67df":function(e,t,a){"use strict";a("22bd")},a137:function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("upload-excel-component",{attrs:{"on-success":e.handleSuccess,"before-upload":e.beforeUpload}}),a("el-button",{attrs:{type:"primary",icon:"el-icon-upload2"},on:{click:e.upload}},[e._v("上传")]),a("el-button",{attrs:{type:"primary",icon:"el-icon-upload2"},on:{click:e.upload_exit}},[e._v("出场")]),a("el-table",{staticStyle:{width:"100%","margin-top":"20px"},attrs:{data:e.tableData,border:"","highlight-current-row":""}},e._l(e.tableHeader,(function(e){return a("el-table-column",{key:e,attrs:{prop:e,label:e}})})),1)],1)},l=[],o=(a("08ba"),function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("input",{ref:"excel-upload-input",staticClass:"excel-upload-input",attrs:{type:"file",accept:".xlsx, .xls"},on:{change:e.handleClick}}),a("div",{staticClass:"drop",on:{drop:e.handleDrop,dragover:e.handleDragover,dragenter:e.handleDragover}},[e._v(" Drop excel file here or "),a("el-button",{staticStyle:{"margin-left":"16px"},attrs:{loading:e.loading,size:"mini",type:"primary"},on:{click:e.handleUpload}},[e._v(" Browse ")])],1)])}),r=[],s=(a("e18c"),a("053b"),a("3f81")),i=a.n(s),c={props:{beforeUpload:Function,onSuccess:Function},data:function(){return{loading:!1,excelData:{header:null,results:null}}},methods:{generateData:function(e){var t=e.header,a=e.results;this.excelData.header=t,this.excelData.results=a,this.onSuccess&&this.onSuccess(this.excelData)},handleDrop:function(e){if(e.stopPropagation(),e.preventDefault(),!this.loading){var t=e.dataTransfer.files;if(1===t.length){var a=t[0];if(!this.isExcel(a))return this.$message.error("Only supports upload .xlsx, .xls, .csv suffix files"),!1;this.upload(a),e.stopPropagation(),e.preventDefault()}else this.$message.error("Only support uploading one file!")}},handleDragover:function(e){e.stopPropagation(),e.preventDefault(),e.dataTransfer.dropEffect="copy"},handleUpload:function(){this.$refs["excel-upload-input"].click()},handleClick:function(e){var t=e.target.files,a=t[0];a&&this.upload(a)},upload:function(e){if(this.$refs["excel-upload-input"].value=null,this.beforeUpload){var t=this.beforeUpload(e);t&&this.readerData(e)}else this.readerData(e)},readerData:function(e){var t=this;return this.loading=!0,new Promise((function(a,n){var l=new FileReader;l.onload=function(e){var n=e.target.result,l=i.a.read(n,{type:"array"}),o=l.SheetNames[0],r=l.Sheets[o],s=t.getHeaderRow(r),c=i.a.utils.sheet_to_json(r);t.generateData({header:s,results:c}),t.loading=!1,a()},l.readAsArrayBuffer(e)}))},getHeaderRow:function(e){var t,a=[],n=i.a.utils.decode_range(e["!ref"]),l=n.s.r;for(t=n.s.c;t<=n.e.c;++t){var o=e[i.a.utils.encode_cell({c:t,r:l})],r="UNKNOWN "+t;o&&o.t&&(r=i.a.utils.format_cell(o)),a.push(r)}return a},isExcel:function(e){return/\.(xlsx|xls|csv)$/.test(e.name)}}},u=c,d=(a("67df"),a("4ac2")),p=Object(d["a"])(u,o,r,!1,null,"d2407dd6",null),f=p.exports,h={name:"UploadExcel",components:{UploadExcelComponent:f},data:function(){return{tableData:[],tableHeader:[],Carlicense:[],Date:[]}},methods:{beforeUpload:function(e){var t=e.size/1024/1024<1;return!!t||(this.$message({message:"Please do not upload files larger than 1m in size.",type:"warning"}),!1)},handleSuccess:function(e){var t=this,a=e.results,n=e.header;this.tableData=a,this.tableHeader=n,console.log("清空"),console.log(this.Carlicense),console.log(this.Date),a.forEach((function(e){t.Carlicense.push(e.Carlicense),t.Date.push(e.Date)}))},upload:function(){console.log(this.Carlicense),console.log(this.Date)},upload_exit:function(){console.log(this.Carlicense),console.log(this.Date)}}},g=h,v=Object(d["a"])(g,n,l,!1,null,null,null);t["default"]=v.exports}}]);