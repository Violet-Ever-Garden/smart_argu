(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1101eba5"],{"0a48":function(t,e,a){"use strict";var n=a("ff48"),o=a.n(n);o.a},"85fb":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"persondata"},[a("div",{staticClass:"line"},[a("span",[t._v("|")]),a("span",[t._v(t._s(t.cropName))])]),a("div",{staticClass:"header"},[a("div",{staticClass:"left"},[a("el-date-picker",{staticStyle:{"margin-right":"1%"},attrs:{type:"date",placeholder:"开始时间"},model:{value:t.value1,callback:function(e){t.value1=e},expression:"value1"}}),a("el-date-picker",{staticStyle:{"margin-right":"1%"},attrs:{type:"date",placeholder:"结束时间"},model:{value:t.value1,callback:function(e){t.value1=e},expression:"value1"}}),a("el-input",{staticStyle:{width:"200px","margin-right":"1%"},attrs:{placeholder:"请输入名称"},model:{value:t.input,callback:function(e){t.input=e},expression:"input"}}),a("el-button",{attrs:{size:"medium"}},[t._v("搜索")])],1),a("div",{staticClass:"right"},[a("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-arrow-left"},on:{click:t.goback}},[t._v("返回")]),a("el-button",{directives:[{name:"show",rawName:"v-show",value:"student"==t.role,expression:"role=='student'"}],attrs:{type:"primary",size:"small",icon:"el-icon-receiving"},on:{click:t.exportData}},[t._v("导出调查数据")]),a("el-button",{directives:[{name:"show",rawName:"v-show",value:"student"==t.role,expression:"role=='student'"}],attrs:{type:"primary",size:"small",icon:"el-icon-document-copy"},on:{click:t.trainningReport}},[t._v("实训报告")]),a("el-button",{directives:[{name:"show",rawName:"v-show",value:"student"==t.role,expression:"role=='student'"}],attrs:{type:"primary",size:"small",icon:"el-icon-document-add"},on:{click:t.addData}},[t._v("新增")])],1)]),a("div",{staticClass:"content"},[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],ref:"multipleTable",staticStyle:{width:"100%"},attrs:{stripe:"",data:t.tableData,"tooltip-effect":"dark"},on:{"selection-change":t.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"77"}}),a("el-table-column",{attrs:{label:"序号",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.$index+1))]}}])}),a("el-table-column",{attrs:{prop:"studentName",label:"姓名",width:"180"}}),a("el-table-column",{attrs:{prop:"className",label:"班级",width:"180"}}),a("el-table-column",{attrs:{prop:"gradeName",label:"年级",width:"180","show-overflow-tooltip":""}}),a("el-table-column",{attrs:{prop:"lastModifiedTime",label:"提交时间",width:"200","show-overflow-tooltip":""}}),a("el-table-column",{attrs:{label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){return t.handleLook(e.$index,e.row)}}},[a("i",{staticClass:"el-icon-view"})]),"student"==t.role?a("el-button",{staticStyle:{color:"green"},attrs:{size:"mini",type:"text"},on:{click:function(a){return t.handleUpdate(e.$index,e.row)}}},[a("i",{staticClass:"el-icon-edit"})]):t._e(),a("el-button",{staticStyle:{color:"red"},attrs:{size:"mini",type:"text"},on:{click:function(a){return t.handleDelete(e.$index,e.row)}}},[a("i",{staticClass:"el-icon-delete"})])]}}])})],1)],1)])},o=[],r=(a("99af"),a("a434"),a("d3b7"),a("3ca3"),a("ddb0"),a("2b3d"),a("bfb7")),i={name:"Persondata",data:function(){return{role:"",cropId:"",cropName:"",studentId:"",input:"",value1:"",tableData:[],multipleSelection:[],listLoading:!0}},created:function(){this.role=this.$store.getters.roles[0],this.cropName=this.$store.getters.cropName,console.log(this.$route.params);var t=this.$route.params,e=t.cropId,a=t.studentId;this.cropId=e,this.studentId=-1==a?this.$store.getters.id:a,console.log(this.studentId),this.getDataReportList()},methods:{getDataReportList:function(){var t=this,e={cropId:this.cropId,studentId:this.studentId};Object(r["h"])(e).then((function(e){t.tableData=e.data.records.splice(0),t.listLoading=!1}))},goback:function(){this.$router.go(-1)},handleSelectionChange:function(t){this.multipleSelection=t},handleLook:function(t,e){this.tableData[t].dataReportId,this.tableData[t].studentName;this.$router.push("/trainning/persondatadetail/".concat(this.tableData[t].dataReportId,"/").concat(this.tableData[t].studentName))},handleUpdate:function(t){var e={cropId:this.cropId,dataReportId:this.tableData[t].dataReportId,studentName:this.tableData[t].studentName};this.$router.push({path:"/trainning/updatedata",query:{data:e}})},handleDelete:function(t){var e=this;this.$confirm("是否确定删除?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){e.listLoading=!0;var a={dataReportId:e.tableData[t].dataReportId};Object(r["a"])(a).then((function(t){e.getDataReportList(),e.$message({type:"success",message:"删除成功!"})}))})).catch((function(){e.$message({type:"info",message:"已取消删除"})}))},trainningReport:function(){this.$router.push("/trainning/studentReport/".concat(this.cropId))},addData:function(){this.$router.push("/trainning/adddata/".concat(this.cropId))},exportData:function(){var t={cropId:this.cropId,studentId:this.studentId};Object(r["b"])(t).then((function(t){var e=new Blob([t.data]),a=document.createElement("a"),n=window.URL.createObjectURL(e);a.href=n,a.download="总数据.xls",document.body.appendChild(a),a.click(),document.body.removeChild(a),window.URL.revokeObjectURL(n)}))}}},s=i,c=(a("0a48"),a("2877")),l=Object(c["a"])(s,n,o,!1,null,"42f4ce7f",null);e["default"]=l.exports},bfb7:function(t,e,a){"use strict";a.d(e,"e",(function(){return o})),a.d(e,"i",(function(){return r})),a.d(e,"d",(function(){return i})),a.d(e,"f",(function(){return s})),a.d(e,"h",(function(){return c})),a.d(e,"g",(function(){return l})),a.d(e,"k",(function(){return d})),a.d(e,"a",(function(){return u})),a.d(e,"c",(function(){return p})),a.d(e,"b",(function(){return h})),a.d(e,"j",(function(){return m}));a("99af"),a("a15b"),a("d81d");var n=a("b775");function o(t){return Object(n["a"])({url:"/class/query",method:"get",params:t})}function r(t){return Object(n["a"])({url:"/asTeacherclass/selectClassManageByTeacherId",method:"get",params:t})}function i(){return Object(n["a"])({url:"/grade/allGrade",method:"get"})}function s(t){return Object(n["a"])({url:"/dataReport/getStudentByCropAndClass",method:"get",params:t})}function c(t){return Object(n["a"])({url:"/dataReport/page?cropId=".concat(t.cropId,"&studentId=").concat(t.studentId),method:"get"})}function l(t){return Object(n["a"])({url:"/dataReport/get/".concat(t.dataReportId),method:"get"})}function d(t){return Object(n["a"])({url:"/dataReport/update",method:"post",data:t})}function u(t){return Object(n["a"])({url:"/dataReport/delete/".concat(t.dataReportId),method:"post"})}function p(t){return Object(n["a"])({url:"/dataReport/exportReportTeacher",method:"get",responseType:"blob",params:t,paramsSerializer:function(e){var a=e.classIds.map((function(t){return"classIds=".concat(t)})).join("&");return"".concat(a,"&cropId=").concat(t.cropId,"&teacherId=").concat(t.teacherId)}})}function h(t){return Object(n["a"])({url:"/dataReport/exportReportStudent",method:"get",responseType:"blob",params:t})}function m(t){return Object(n["a"])({url:"/dataReport/statisticalAnalysis",method:"get",params:{ids:[t]},paramsSerializer:function(t){var e=t.ids.map((function(t){return"ids=".concat(t)})).join("&");return"".concat(e)}})}},ff48:function(t,e,a){}}]);