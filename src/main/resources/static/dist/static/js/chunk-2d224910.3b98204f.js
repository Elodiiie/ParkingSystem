(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d224910"],{e14b:function(e,r,t){"use strict";t.r(r);var l=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticStyle:{padding:"100px",overflow:"auto"}},[t("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",staticStyle:{width:"40%"},attrs:{model:e.ruleForm,rules:e.rules,"label-width":"100px"}},[t("el-form-item",{attrs:{label:"车牌",prop:"carlicense"}},[t("el-input",{model:{value:e.ruleForm.carlicense,callback:function(r){e.$set(e.ruleForm,"carlicense",r)},expression:"ruleForm.carlicense"}})],1),t("el-form-item",{attrs:{label:"时间",required:""}},[t("div",{staticClass:"block"},[t("el-date-picker",{attrs:{type:"datetime",placeholder:"选择日期时间",align:"right","value-format":"yyyy-MM-dd HH:mm:ss",format:"yyyy-MM-dd HH:mm:ss"},model:{value:e.ruleForm.time,callback:function(r){e.$set(e.ruleForm,"time",r)},expression:"ruleForm.time"}})],1)]),t("el-form-item",[t("el-button",{attrs:{type:"primary"},on:{click:function(r){return e.submitForm("ruleForm")}}},[e._v("修改")]),t("el-button",{on:{click:function(r){return e.resetForm("ruleForm")}}},[e._v("重置")])],1)],1)],1)},a=[],o={name:"AddParking",data:function(){return{ruleForm:{carlicense:"",time:""},rules:{carlicense:[{required:!0,message:"请输入车牌",trigger:"blur"},{min:7,max:8,message:"请输入正确车牌格式",trigger:"blur"}],time:[{required:!0,message:"请选择时间",trigger:"blur"}]}}},methods:{submitForm:function(e){var r=this;console.log(this.ruleForm);var t=this;this.$refs[e].validate((function(e){if(!e)return alert("error submit!"),!1;r.$axios.post("http://localhost:8080/parking/addParkingRecord",r.ruleForm).then((function(e){console.log(e.data),2e4===e.data.code?(t.$axios.post("http://localhost:8080/parking/sendMess",t.ruleForm),t.$alert("添加成功","提示",{confirmButtonText:"确定",callback:function(e){t.$router.push({path:"/manage/parkspace"})}})):t.$message.error("添加失败--"+e.data.message)}))}))},resetForm:function(e){this.$refs[e].resetFields()}}},s=o,i=t("4ac2"),n=Object(i["a"])(s,l,a,!1,null,"60c075d1",null);r["default"]=n.exports}}]);