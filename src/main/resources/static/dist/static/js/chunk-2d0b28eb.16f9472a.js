(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0b28eb"],{"252d":function(e,r,t){"use strict";t.r(r);var a=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticStyle:{padding:"100px",overflow:"auto"}},[t("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",staticStyle:{width:"40%"},attrs:{model:e.ruleForm,rules:e.rules,"label-width":"100px"}},[t("el-form-item",{attrs:{label:"用户",prop:"username"}},[t("el-input",{attrs:{disabled:""},model:{value:e.ruleForm.username,callback:function(r){e.$set(e.ruleForm,"username",r)},expression:"ruleForm.username"}})],1),t("el-form-item",{attrs:{label:"时间",required:""}},[t("div",{staticClass:"block"},[t("el-date-picker",{attrs:{type:"datetime",placeholder:"选择日期时间",align:"right","value-format":"yyyy-MM-dd HH:mm:ss",format:"yyyy-MM-dd HH:mm:ss"},model:{value:e.ruleForm.time,callback:function(r){e.$set(e.ruleForm,"time",r)},expression:"ruleForm.time"}})],1)]),t("el-form-item",{attrs:{label:"缴费金额",prop:"fare"}},[t("el-input",{model:{value:e.ruleForm.fare,callback:function(r){e.$set(e.ruleForm,"fare",r)},expression:"ruleForm.fare"}})],1),t("el-form-item",[t("el-button",{attrs:{type:"primary"},on:{click:function(r){return e.submitForm("ruleForm")}}},[e._v("修改")]),t("el-button",{on:{click:function(r){return e.resetForm("ruleForm")}}},[e._v("重置")])],1)],1)],1)},o=[],l={name:"UpdatePay",data:function(){return{ruleForm:{payid:"",username:"",fare:"",time:""},rules:{fare:[{required:!0,message:"请选择扣费金额",trigger:"change"}]}}},created:function(){var e=this;this.$axios.get("http://localhost:8080/pay/findById/"+this.$route.query.id).then((function(r){e.ruleForm.username=e.$route.query.username,e.ruleForm=r.data,e.ruleForm.time=r.data.time,console.log(e.ruleForm.value1)}))},methods:{submitForm:function(e){var r=this;console.log(this.ruleForm);var t=this;this.$refs[e].validate((function(e){if(!e)return alert("error submit!"),!1;r.$axios.post("http://localhost:8080/pay/updateRecord",r.ruleForm).then((function(e){console.log(e.data),2e4===e.data.code?t.$alert("修改成功","提示",{confirmButtonText:"确定",callback:function(e){t.$router.push({path:"/managefin/Finance"})}}):t.$message.error("更新失败--"+e.data.message)}))}))},resetForm:function(e){this.$refs[e].resetFields()}}},s=l,u=t("4ac2"),i=Object(u["a"])(s,a,o,!1,null,"770f3adc",null);r["default"]=i.exports}}]);