<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.NumberFormat" %>

<%--
  User: Rui
  Date: 2017/12/27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../htmlframe/headFrame.jsp"></jsp:include>
<jsp:include page="../htmlframe/headerFrame.jsp"></jsp:include>
<style type="text/css">
    /*スクロール用*/
    .tdwidth50 {
        width :50pt;
    }
    .tdwidth120 {
        width :120pt;
    }
</style>
<body class="skin-green">
<!-- header logo: style can be found in header.less -->

<div class="wrapper row-offcanvas row-offcanvas-left">


    <aside class="">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>在庫一覧</h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- collection -->
            <div class="col-xs-12">
                <div class="nav-tabs-custom">
                    <div class="tab-content">
                        <form id="searchInput" name="searchInput" method="post">
                            <div class="row form-group">
                                <div class="col-md-2">
                                    <select name="big_type" id="big_type" style="width: 200px">
                                        <option value="0">大分類</option>
                                        <c:forEach items="${stockBigtypeList}" var="tempBigtype">
                                            <option value="${tempBigtype.bigtypeId}">${tempBigtype.bigtypeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <select name="middle_type" id="middle_type" style="width: 200px">
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <select name="small_type" id="small_type" style="width: 200px">
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <select name="warehouse_list" id="warehouse_list" style="width: 200px">
                                        <option value="0">倉庫</option>
                                        <c:forEach items="${stockWarehouseList}" var="tempWarehouse">
                                            <option value="${tempWarehouse.warehouseId}">${tempWarehouse.warehouseName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row form-group">
                                <div class="col-md-2">
                                    <input type="button" id="master-location-confirm" class="btn btn-block btn-success"
                                           onclick="searchStockData()" value="検索">
                                </div>
                            </div>

                        </form>
                    </div>

                    <div class="tab-content">
                        <div class="tab-pane active" id="tab_1">
                            <table class="tablesorter table table-hover valve-table" id="stockListDataSort">
                                <thead>
                                <tr>
                                    <th>No</th>
                                    <th>商品名</th>
                                    <th>バーコード</th>
                                    <th>数量</th>
                                    <th>倉庫</th>
                                    <th>単位</th>
                                    <%--<th>操作</th>--%>
                                </tr>
                                </thead>
                                <tbody id="stockListData">
                                <c:forEach items="${stockList}" var="stockList" varStatus="status">
                                    <tr id="${stockList.stockListId}">
                                        <td><c:out value="${status.count}"/></td>
                                        <td>${stockList.warehouse.warehouseName}</td>
                                        <td>${stockList.goodsList.goodsListName}</td>
                                        <td>${stockList.goodsList.goodsBarcode}</td>
                                        <td>${stockList.stockputNum}</td>
                                        <td>${stockList.goodsList.goodsUnitName}</td>
                                    </tr>
                                </c:forEach>
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
<!-- ====================== -->
<!-- MediaTableのスクリプト -->
<!-- ====================== -->
<script type="text/javascript">
    $(document).ready(function() {
                $("#stockListDataSort").tablesorter({
//                    headers: {
//                        3: { sorter: false },
//                        6: { sorter: false },
//                        7: { sorter: false }
//                    }
                });
            }

    );
</script>

<script type="text/javascript">

    $(document).ready(function () {
        //The Calender
        $('#sandbox-container .input-daterange').datepicker({
            format: 'yyyy/mm/dd',
            language: 'ja',
            autoclose: true,
            todayHighlight: true
        });


        //大分類により中分類を取得
        $("#big_type").change(function () {
            var bigtypeValue = $("[name=big_type]").val();
            if (bigtypeValue == "大分類") {
                bigtypeValue = "";
            }
            //設定
            $("#middle_type").val("");
            $("#small_type").val("");
            console.log("bittypeValue=" + bigtypeValue);
            $.post("/DataManager/type/getMiddleTypeByBigType", {"outInput": "1","bigtypeValue": bigtypeValue}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeUpMiddleType(items);


                //小分類を設定
                var middletypeValue = $("[name=middle_type]").val();
                //sessionに保存
                console.log("middletypeValue=" + middletypeValue);
                $.post("/DataManager/type/getSmallTypeByBigType", {"outInput": "6","bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue}, function (data) {
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
            $.post("/DataManager/type/getSmallTypeByBigType", {"outInput": "5","bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue}, function (data) {
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


    //商品リストを取得
    function makeUpStockList(items) {
        $("#stockListData").html("");
        var htmlContent = "";
        for (var i = 0; i < items.length; i++) {


            htmlContent =
                    htmlContent + '' +
                    '<tr id="' + items[i].stockListId + '">' +
                    '<td>' + (i + 1) + '</td>' +
                    '<td>' + items[i].warehouse.warehouseName + '</td>' +
                    '<td>' + items[i].goodsList.goodsListName + '</td>' +
                    '<td>' + items[i].goodsList.goodsBarcode + '</td>' +
                    '<td>' + items[i].stockputNum + '</td>' +
                    '<td>' + items[i].goodsList.goodsUnitName + '</td>' +
                    '</tr>';
        }
        $("#stockListData").html(htmlContent);
        $("#stockListDataSort").trigger("update");
    }

    //在庫検索
    function searchStockData() {

        //検索結果リストを空にする
        $("#stockListData").html("");

        //キーワード
        var bigtypeValue = $("[name=big_type]").val();
        var middletypeValue = $("[name=middle_type]").val();
        var smalltypeValue = $("[name=small_type]").val();
        var wareHouseValue = $("[name=warehouse_list]").val();

        console.log("Goods bigtypeValue =" + bigtypeValue);
        console.log("Goods middletypeValue =" + middletypeValue);
        console.log("Goods smalltypeValue =" + smalltypeValue);
        console.log("Goods stock_list =" + wareHouseValue);


        //大分類を選択しなかった場合は、検索しない
        if (bigtypeValue == "0" || middletypeValue == null || smalltypeValue == null) {
            alert("各分類を選択してから検索してください")
            return false;
        } else {
            $.post("/DataManager/stock/getStockListByKeywords", {"bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue, "smalltypeValue": smalltypeValue, "wareHouseValue": wareHouseValue}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeUpStockList(items);
            });
        }
    }



</script>

</body>
</html>