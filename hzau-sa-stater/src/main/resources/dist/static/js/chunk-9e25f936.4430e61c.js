(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-9e25f936"],{"956a":function(t,e,n){"use strict";var o=n("d717"),a=n.n(o);a.a},d717:function(t,e,n){},d857:function(t,e,n){"use strict";n.r(e);var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[t._m(0),n("div",{staticClass:"content"},t._l(t.operations,(function(e,o){return n("el-card",{key:o,staticClass:"card"},[n("div",{staticClass:"content-image"},[n("i",{staticClass:"el-icon-set-up"})]),n("div",{staticClass:"middle"},[t._v(" "+t._s(e.remoteControlDeviceName)+" ")]),n("div",{staticClass:"foot"},[n("el-switch",{attrs:{"active-value":"开启","inactive-value":"关闭",width:60,"active-color":"#13ce66","inactive-color":"#909399"},on:{change:function(e){return t.changeControl(o)}},model:{value:e.remoteStatus,callback:function(n){t.$set(e,"remoteStatus",n)},expression:"item.remoteStatus"}})],1)])})),1)])},a=[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"line"},[n("span",[t._v("|")]),n("span",[t._v("水肥机")])])}],r=n("b775");function i(t){return Object(r["a"])({url:"/controlInteraction/pageByStudentId",method:"get",params:{studentId:t.Id,limit:"10",page:"1"}})}function s(t){return Object(r["a"])({url:"/controlInteraction/pageByTeacherId",method:"get",params:{teacherId:t.Id,limit:"10",page:"1"}})}function c(t){return Object(r["a"])({url:"/controlInteraction/updateControlInteraction",method:"post",params:{controlInteractionId:t.controlInteractionId,remoteStatus:t.remoteStatus}})}var l={data:function(){return{role:"",operations:[]}},created:function(){var t=this;this.role=this.$store.getters.roles[0];this.$store.getters.id;var e={Id:"jiang"};"student"==this.role?i(e).then((function(e){t.operations=e.data.records})):s(e).then((function(e){t.operations=e.data.records}))},methods:{newfetch:function(){var t=this,e=(this.$store.getters.id,{Id:"jiang"});"student"==this.role?i(e).then((function(e){t.operations=e.data.records})):s(e).then((function(e){t.operations=e.data.records}))},changeControl:function(t){var e=this;console.log(this.operations[t]);var n={controlInteractionId:this.operations[t].controlInteractionId,remoteStatus:this.operations[t].remoteStatus};console.log(n),c(n).then((function(t){console.log(t),e.newfetch()}))}}},u=l,d=(n("956a"),n("2877")),h=Object(d["a"])(u,o,a,!1,null,"0e85efdf",null);e["default"]=h.exports}}]);