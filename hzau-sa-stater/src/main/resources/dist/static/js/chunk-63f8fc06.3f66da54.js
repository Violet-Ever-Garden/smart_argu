(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-63f8fc06"],{"48acb":function(t,e,a){"use strict";a.r(e);var r=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"adddata"},[a("div",{staticClass:"header"},[a("div",{staticClass:"left"},[a("span",[t._v("作物：")]),a("span",[t._v(t._s(t.cropName))])]),a("div",{staticClass:"right"},[a("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-arrow-left"},on:{click:t.goback}},[t._v("返回")]),a("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-message"},on:{click:t.save}},[t._v("保存")])],1)]),a("div",{staticClass:"line"}),a("div",{staticClass:"content"},[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],staticStyle:{width:"100%"},attrs:{stripe:"",data:t.tableData,border:""}},[a("el-table-column",{attrs:{width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{icon:"el-icon-plus",circle:""},on:{click:t.addRow}}),a("el-button",{attrs:{icon:"el-icon-minus",circle:""},on:{click:function(a){return t.deleteRow(e.$index)}}})]}}])},[a("template",{slot:"header"},[a("el-button",{attrs:{icon:"el-icon-plus",circle:""},on:{click:t.addRow}})],1)],2),a("el-table-column",{attrs:{label:"属性",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-select",{attrs:{placeholder:"请选择"},model:{value:t.tableData[e.$index].cropProperty,callback:function(a){t.$set(t.tableData[e.$index],"cropProperty",a)},expression:"tableData[scope.$index].cropProperty"}},t._l(t.propOptions,(function(t){return a("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})})),1)]}}])}),a("el-table-column",{attrs:{label:"检测时间",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-date-picker",{staticStyle:{width:"160px"},attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss",placeholder:"选择日期时间"},model:{value:t.tableData[e.$index].detectionTime,callback:function(a){t.$set(t.tableData[e.$index],"detectionTime",a)},expression:"tableData[scope.$index].detectionTime"}})]}}])}),a("el-table-column",{attrs:{label:"生育期",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-select",{attrs:{placeholder:"请选择"},model:{value:t.tableData[e.$index].growthPeriod,callback:function(a){t.$set(t.tableData[e.$index],"growthPeriod",a)},expression:"tableData[scope.$index].growthPeriod"}},t._l(t.durationOptions,(function(t){return a("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})})),1)]}}])}),a("el-table-column",{attrs:{label:"处理",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-input",{model:{value:t.tableData[e.$index].process,callback:function(a){t.$set(t.tableData[e.$index],"process",a)},expression:"tableData[scope.$index].process"}})]}}])}),t._l(t.extraParam,(function(e,r){return a("el-table-column",{key:r+"extra",attrs:{label:e,width:"180"},scopedSlots:t._u([{key:"default",fn:function(r){return[a("el-input",{on:{click:function(a){return t.changeInput(t.tableData[r.$index].extraParam[""+e])}},model:{value:t.tableData[r.$index].extraParam[""+e],callback:function(a){t.$set(t.tableData[r.$index].extraParam,""+e,a)},expression:"tableData[scope.$index].extraParam[`${item}`]"}})]}}],null,!0)})})),a("el-table-column",{attrs:{width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-input",{model:{value:t.tableData[e.$index].data[0],callback:function(a){t.$set(t.tableData[e.$index].data,0,a)},expression:"tableData[scope.$index].data[0]"}})]}}])},[a("template",{slot:"header"},[a("span",{staticStyle:{"margin-right":"4%"}},[t._v("数据1")]),a("el-button",{attrs:{icon:"el-icon-plus",circle:"",size:"mini"},on:{click:t.addProp}})],1)],2),t._l(t.propTotal,(function(e,r){return a("el-table-column",{key:r+"prop",attrs:{width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-input",{model:{value:t.tableData[e.$index].data[r+1],callback:function(a){t.$set(t.tableData[e.$index].data,r+1,a)},expression:"tableData[scope.$index].data[index+1]"}})]}}],null,!0)},[a("template",{slot:"header"},[a("span",{staticStyle:{"margin-right":"4%"}},[t._v(t._s(e))]),a("el-button",{attrs:{icon:"el-icon-plus",circle:"",size:"mini"},on:{click:t.addProp}}),a("el-button",{attrs:{icon:"el-icon-minus",circle:"",size:"mini"},on:{click:function(e){return t.deleteProp(r)}}})],1)],2)})),a("el-table-column",{attrs:{width:"180",label:"平均数"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("div",{staticClass:"column",on:{click:function(a){return t.getAverage(e.$index)}}},[t._v(t._s(" "+t.tableData[e.$index].average))])]}}])})],2)],1)])},n=[],o=(a("fb6a"),a("a434"),a("8f7b")),c=a("bfb7"),i={name:"adddata",data:function(){return{listLoading:!0,role:"",cropId:"",cropName:"",id:"",dataReportId:"",studentId:"",propTotal:[],extraParam:[],propOptions:[],durationOptions:[],templateData:{cropProperty:"",detectionTime:"",growthPeriod:"",process:"",extraParam:{},data:[],average:""},tableData:[{cropProperty:"",detectionTime:"",growthPeriod:"",process:"",extraParam:{},data:[],average:""}]}},methods:{goback:function(){this.$router.go(-1)},addProp:function(){var t=this.propTotal.length;this.propTotal.splice(t,0,"数据".concat(t+2))},deleteProp:function(t){console.log(t);var e=this.propTotal.length;this.propTotal.splice(e-1,1);for(var a=0;a<this.tableData.length;a++)this.tableData[a].data.splice(t+1,1)},addRow:function(){var t=this.tableData.length,e={};for(var a in this.templateData)e[a]=this.templateData[a];this.tableData.splice(t,0,e)},deleteRow:function(t){this.tableData.splice(t,1)},save:function(){var t=this;console.log(this.tableData),console.log(this.extraParam);var e={cropDatas:this.tableData,cropId:this.cropId,studentId:this.studentId,id:this.id,dataReportId:this.dataReportId};Object(c["k"])(e).then((function(e){console.log(e),t.$message({type:"success",message:"保存成功!"})}))},getCropParameter:function(t){var e=this;Object(o["f"])(t).then((function(t){if(t.data.length>4)for(var a=t.data.slice(4),r=0;r<a.length;r++){var n=a[r].parameterName;e.extraParam.push(n);for(var o=0;o<e.tableData.length;o++)e.$set(e.tableData[o].extraParam,"".concat(n),"");e.$set(e.templateData.extraParam,"".concat(n),""),console.log(e.templateData)}}))},getCropProperty:function(t){var e=this;Object(o["g"])(t).then((function(t){for(var a=0;a<t.data.length;a++)e.propOptions.push({value:"".concat(t.data[a].propertyName),label:"".concat(t.data[a].propertyName)});console.log(e.propOptions)}))},getCropGrowthPeriod:function(t){var e=this;Object(o["e"])(t).then((function(t){for(var a=0;a<t.data.length;a++)e.durationOptions.push({value:"".concat(t.data[a].growthPeriodName),label:"".concat(t.data[a].growthPeriodName)})}))},getAverage:function(t){console.log("okk");for(var e=this.tableData[t].data,a=0,r=0;r<e.length;r++)a+=parseFloat(e[r]);e.length>0&&this.$set(this.tableData[t],"average",a/e.length)},getDataReport:function(t){var e=this;Object(c["g"])(t).then((function(t){if(e.id=t.data.id,e.tableData=t.data.cropDatas,e.tableData.length&&e.tableData[0].data.length)for(var a=1;a<e.tableData[0].data.length;a++)e.propTotal.push("数据".concat(a+1));e.listLoading=!1}))}},created:function(){this.role=this.$store.getters.roles[0],this.cropName=this.$store.getters.cropName,this.studentId=this.$store.getters.id,this.cropId=this.$route.query.data.cropId,this.dataReportId=this.$route.query.data.dataReportId;var t={cropId:this.cropId};this.getCropParameter(t),this.getCropProperty(t),this.getCropGrowthPeriod(t);var e={dataReportId:this.dataReportId};this.getDataReport(e)}},l=i,s=(a("901e"),a("2877")),d=Object(s["a"])(l,r,n,!1,null,"600d4aee",null);e["default"]=d.exports},"62fb":function(t,e,a){},"8f7b":function(t,e,a){"use strict";a.d(e,"j",(function(){return n})),a.d(e,"h",(function(){return o})),a.d(e,"a",(function(){return c})),a.d(e,"k",(function(){return i})),a.d(e,"c",(function(){return l})),a.d(e,"d",(function(){return s})),a.d(e,"i",(function(){return d})),a.d(e,"f",(function(){return u})),a.d(e,"g",(function(){return p})),a.d(e,"e",(function(){return f})),a.d(e,"b",(function(){return h}));a("99af");var r=a("b775");function n(t){return Object(r["a"])({url:"/crop/page?keyword=".concat(t.keyword,"&page=").concat(t.page,"&limit=").concat(t.limit),method:"get"})}function o(t){return Object(r["a"])({url:"/crop/listWithUrl?cropName=".concat(t.cropName),method:"get"})}function c(t){return Object(r["a"])({url:"/crop/save",method:"post",headers:{"Content-Type":"multipart/form-data"},data:t})}function i(t){return Object(r["a"])({url:"/crop/update",method:"post",headers:{"Content-Type":"multipart/form-data"},data:t})}function l(t){return Object(r["a"])({url:"/crop/delete/".concat(t.cropId),method:"post"})}function s(t){return Object(r["a"])({url:"/crop/deleteList?ids%5B%5D=".concat(t.item),method:"post"})}function d(){return Object(r["a"])({url:"/crop/listWithUrl",method:"get"})}function u(t){return Object(r["a"])({url:"/cropParameter/page",method:"get",params:t})}function p(t){return Object(r["a"])({url:"/cropProperty/list/".concat(t.cropId),method:"get"})}function f(t){return Object(r["a"])({url:"/cropGrowthPeriod/list/".concat(t.cropId),method:"get"})}function h(t){return Object(r["a"])({url:"/dataReport/save",method:"post",data:t})}},"901e":function(t,e,a){"use strict";var r=a("62fb"),n=a.n(r);n.a},bfb7:function(t,e,a){"use strict";a.d(e,"e",(function(){return n})),a.d(e,"i",(function(){return o})),a.d(e,"d",(function(){return c})),a.d(e,"f",(function(){return i})),a.d(e,"h",(function(){return l})),a.d(e,"g",(function(){return s})),a.d(e,"k",(function(){return d})),a.d(e,"a",(function(){return u})),a.d(e,"c",(function(){return p})),a.d(e,"b",(function(){return f})),a.d(e,"j",(function(){return h}));a("99af"),a("a15b"),a("d81d");var r=a("b775");function n(t){return Object(r["a"])({url:"/class/query",method:"get",params:t})}function o(t){return Object(r["a"])({url:"/asTeacherclass/selectClassManageByTeacherId",method:"get",params:t})}function c(){return Object(r["a"])({url:"/grade/allGrade",method:"get"})}function i(t){return Object(r["a"])({url:"/dataReport/getStudentByCropAndClass",method:"get",params:t})}function l(t){return Object(r["a"])({url:"/dataReport/page?cropId=".concat(t.cropId,"&studentId=").concat(t.studentId),method:"get"})}function s(t){return Object(r["a"])({url:"/dataReport/get/".concat(t.dataReportId),method:"get"})}function d(t){return Object(r["a"])({url:"/dataReport/update",method:"post",data:t})}function u(t){return Object(r["a"])({url:"/dataReport/delete/".concat(t.dataReportId),method:"post"})}function p(t){return Object(r["a"])({url:"/dataReport/exportReportTeacher",method:"get",responseType:"blob",params:t,paramsSerializer:function(e){var a=e.classIds.map((function(t){return"classIds=".concat(t)})).join("&");return"".concat(a,"&cropId=").concat(t.cropId,"&teacherId=").concat(t.teacherId)}})}function f(t){return Object(r["a"])({url:"/dataReport/exportReportStudent",method:"get",responseType:"blob",params:t})}function h(t){return Object(r["a"])({url:"/dataReport/statisticalAnalysis",method:"get",params:{ids:[t]},paramsSerializer:function(t){var e=t.ids.map((function(t){return"ids=".concat(t)})).join("&");return"".concat(e)}})}}}]);