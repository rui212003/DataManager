<%--
  User: Rui
  Date: 2017/12/27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../htmlframe/headFrame.jsp"></jsp:include>
<jsp:include page="../../htmlframe/headerFrame.jsp"></jsp:include>
<style type="text/css">
    /*スクロール用*/
    .tdwidth50 {
        width :50pt;
    }
    .tdwidth70 {
        width :70pt;
    }
    .tdwidth90 {
        width :90pt;
    }
    .tdwidth120 {
        width :120pt;
    }
    .tdwidth300 {
        width :300pt;
    }
</style>
<body class="skin-green">
	<!-- header logo: style can be found in header.less -->

	<div class="wrapper row-offcanvas row-offcanvas-left">


		<aside class="">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>商品名称</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<!-- collection -->
				<div class="col-xs-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
                            <div class="row form-group">
                                <div class="col-md-2">
                                    <select name="big_type" id="big_type" style="width: 200px">
                                        <option value="0">大分類</option>
                                        <c:forEach items="${masterBigtypeList}" var="tempBigtype">
                                            <option value="${tempBigtype.bigtypeId}">${tempBigtype.bigtypeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <select name="middle_type" id="middle_type" style="width: 200px">
                                        <%--<c:forEach items="${masterGoodsListMiddletypeList}" var="tempMiddletype">--%>
                                            <%--<option value="${tempMiddletype.middletypeId}">${tempMiddletype.middletypeName}</option>--%>
                                        <%--</c:forEach>--%>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <select name="small_type" id="small_type" style="width: 200px">
                                        <%--<c:forEach items="${masterGoodsListSmalltypeList}" var="tempSamlltype">--%>
                                            <%--<option value="${tempSamlltype.smalltypeId}">${tempSamlltype.smalltypeName}</option>--%>
                                        <%--</c:forEach>--%>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <input type="button" id="master-location-confirm" class="btn btn-block btn-success"
                                           onclick="searchGoodsListData()" value="検索">
                                </div>
                                <div class="col-md-2">
                                    <span id="goodsListData_num" class="goodsListData_num " STYLE="font-size: 16px;">合計 ${goodsListSearchData_num} 件</span>
                                </div>
                            </div>

						</div>
						<div class="tab-content">
							<div class="col-md-2">
								<input type="button"  class="btn btn-block btn-success" onclick="addGoodsList()"  value="新規追加">
							</div>
						</div>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<table class="table table-hover valve-table">
									<thead>
										<tr>
											<th>No</th>
											<th>商品名</th>
											<th>バーコード</th>
											<th>単位</th>
											<th>定価(円)</th>
											<th>売価額(元)</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="goodsListData">
									</tbody>
								</table>
							</div>
							<!-- /.tab-pane -->
						</div>
						<!-- /.tab-content -->
					</div>
				</div>
			</section>
			<!-- /.content -->
		</aside>
		<!-- /.right-side -->
	</div>
	<!-- ./wrapper -->

	<!-- add new calendar event modal -->

	<script type="text/javascript">
		$(document).ready(function() {
			//The Calender
			$('#sandbox-container .input-daterange').datepicker({
				format : 'yyyy-mm-dd',
				language : 'ja',
				autoclose : true,
				todayHighlight : true
			});



            //大分類により中分類を取得
            $("#big_type").change(function () {
                var bigtypeValue = $("[name=big_type]").val();
                if (bigtypeValue == "0") {
                    bigtypeValue = "";
                }
                //設定
                $("#middle_type").val("");
                $("#small_type").val("");
                console.log("bittypeValue=" + bigtypeValue);
                $.post("/DataManager/type/getMiddleTypeByBigType", {"outInput": "2","bigtypeValue": bigtypeValue}, function (data) {
                    if (data == "login") {
                        logoutFun();
                    }
                    var items = JSON.parse(data);
                    makeUpMiddleType(items);


                    //小分類を設定
                    var middletypeValue = $("[name=middle_type]").val();
                    //sessionに保存
                    console.log("middletypeValue=" + middletypeValue);
                    $.post("/DataManager/type/getSmallTypeByBigType", {"outInput": "5","bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue}, function (data) {
                        if (data == "login") {
                            logoutFun();
                        }
                        var items = JSON.parse(data);
                        makeUpSmallType(items);
                    });
                });
            });

            //中分類により小分類を取得
            $("#middle_type").change(function () {
                //小分類を設定
                var bigtypeValue = $("[name=big_type]").val();
                var middletypeValue = $("[name=middle_type]").val();

                //設定
                $("#small_type").val("");
                console.log("middletypeValue=" + middletypeValue);
                $.post("/DataManager/type/getSmallTypeByBigType", {"outInput": "4","bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue}, function (data) {
                    if (data == "login") {
                        logoutFun();
                    }
                    var items = JSON.parse(data);
                    makeUpSmallType(items);
                });

            });


		});

        //中分類を取得
        function makeUpMiddleType(items) {
            $("#middle_type").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {
                htmlContent =
                        htmlContent + '' +
                        '<option value=' + items[i].middletypeId + '>' + items[i].middletypeName + '</option>'
            }
            $("#middle_type").html(htmlContent);
        }

        //小分類を取得
        function makeUpSmallType(items) {
            $("#small_type").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {
                htmlContent =
                        htmlContent + '' +
                        '<option value=' + items[i].smalltypeId + '>' + items[i].smalltypeName + '</option>'
            }
            $("#small_type").html(htmlContent);
        }

        //商品検索
        function searchGoodsListData() {

            //検索結果リストを空にする
            $("#goodsListData").html("");

            //キーワード
            var bigtypeValue = $("[name=big_type]").val();
            var middletypeValue = $("[name=middle_type]").val();
            var smalltypeValue = $("[name=small_type]").val();


            console.log("Goods bigtypeValue =" + bigtypeValue);
            console.log("Goods middletypeValue =" + middletypeValue);
            console.log("Goods smalltypeValue =" + smalltypeValue);


            //大分類を選択しなかった場合は、検索しない
            if (bigtypeValue == "0" || middletypeValue == null || smalltypeValue == null ) {
                alert("各分類を選択してから検索してください")
                return false;
            } else {
                $.post("/DataManager/goodsList/getGoodsListByKeywords", {"bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue, "smalltypeValue": smalltypeValue}, function (data) {
                    if (data == "login") {
                        logoutFun();
                    }
                    var items = JSON.parse(data);
                    makeUpGoodsList(items);
                });
            }
        }


        //商品リストを取得
        function makeUpGoodsList(items) {
            $("#goodsListData").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {

                var goodsListName="goodsListName-"+items[i].goodsListId;
                var goodsBarcode="goodsBarcode-"+items[i].goodsListId;
                var goodsPriceJP="goodsPriceJP-"+items[i].goodsListId;
                var goodsPriceCH="goodsPriceCH-"+items[i].goodsListId;
                var goodsUnitName="goodsUnitName-"+items[i].goodsListId;


                htmlContent =
                        htmlContent + '' +
                        '<tr id="' + items[i].goodsListId + '">' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="商品名"  name="'+goodsListName+'"  id="'+goodsListName+'" value="' + items[i].goodsListName + '"/></td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="バーコード"  name="'+goodsBarcode+'"  id="'+goodsBarcode+'" value="' + items[i].goodsBarcode + '"/></td>' +
                        '<td> <input class="tdwidth50" type="text" placeholder="単位"  name="'+goodsUnitName+'"  id="'+goodsUnitName+'" value="' + items[i].goodsUnitName + '"/></td>' +
                        '<td> <input class="tdwidth50" type="text" placeholder="定価（円）"  name="'+goodsPriceJP+'"  id="'+goodsPriceJP+'" value="' + items[i].goodsPriceJP + '"/></td>' +
                        '<td> <input class="tdwidth50" type="text" placeholder="売価（元）"  name="'+goodsPriceCH+'"  id="'+goodsPriceCH+'" value="' + items[i].goodsPriceCH + '"/></td>' +
                        '<td>' +
                        '<button onclick="updateGoodsList(' + items[i].goodsListId + ')" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;'+
                        '<button onclick="deleteGoodsList(' + items[i].goodsListId + ')" class="btn btn-danger operation-button-btn">削除</button>'
                '</td>' +
                '</tr>';
            }
            $("#goodsListData").html(htmlContent);
//            $('#outputData_num').text('合計 '+items.length+" 件");
//            $("#outputDataList").trigger("update");
        }

        //商品修正
        function updateGoodsList(obj) {
            //キーワード
            var goodsListId=obj;
            var goodsListName=document.getElementById("goodsListName-"+goodsListId).value;
            var goodsBarcode=document.getElementById("goodsBarcode-"+goodsListId).value;
            var goodsPriceJP=document.getElementById("goodsPriceJP-"+goodsListId).value;
            var goodsPriceCH=document.getElementById("goodsPriceCH-"+goodsListId).value;
            var goodsUnitName=document.getElementById("goodsUnitName-"+goodsListId).value;

            //商品を修正
            $.post("/DataManager/goodsList/updateGoodsList", {"goodsListId": goodsListId,"goodsListName": goodsListName,"goodsBarcode": goodsBarcode,"goodsPriceJP": goodsPriceJP,"goodsPriceCH": goodsPriceCH,"goodsUnitName": goodsUnitName}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
            });
        }

        //商品削除
        function deleteGoodsList(obj) {
            //キーワード
            var goodsListId=obj;

            if (!confirm("この行を削除しますか？"))

                return;
            $.post("/DataManager/goodsList/deleteGoodsList", {"goodsListId": goodsListId}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeUpGoodsList(items);
            });


        }

        //商品を追加する
        function addGoodsList() {

            //キーワード
            var bigtypeValue = $("[name=big_type]").val();
            var middletypeValue = $("[name=middle_type]").val();
            var smalltypeValue = $("[name=small_type]").val();


            console.log("Goods bigtypeValue =" + bigtypeValue);
            console.log("Goods middletypeValue =" + middletypeValue);
            console.log("Goods smalltypeValue =" + smalltypeValue);


            //大分類を選択しなかった場合は、検索しない
            if (bigtypeValue == "0" || middletypeValue == null || smalltypeValue == null ) {
                alert("各分類を選択してから検索してください")
                return false;
            } else {
                //商品を追加する
                $.post("/DataManager/goodsList/addGoodsList", {"bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue, "smalltypeValue": smalltypeValue}, function (data) {
                    if (data == "login") {
                        logoutFun();
                    }

                    var items = JSON.parse(data);
                    makeUpGoodsList(items);
                });
            }


        }



    </script>

</body>
</html>