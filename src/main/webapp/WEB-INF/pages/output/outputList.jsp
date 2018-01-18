<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.NumberFormat" %>
<%
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(0);
    nf.setMinimumFractionDigits(0);
%>
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
            <h1>出庫一覧</h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- collection -->
            <div class="col-xs-12">
                <div class="nav-tabs-custom">
                    <div class="tab-content">
                        <form id="searchOutput" name="searchOutput" method="post">
                            <div class="row form-group">
                                <div class="col-md-2">
                                    <select name="big_type" id="big_type" style="width: 200px">
                                        <option value="0">大分類</option>
                                        <c:forEach items="${outputBigtypeList}" var="tempBigtype">
                                            <option value="${tempBigtype.bigtypeId}">${tempBigtype.bigtypeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <select name="middle_type" id="middle_type" style="width: 200px">
                                        <c:forEach items="${outputmiddletypeList}" var="tempMiddletype">
                                            <option value="${tempMiddletype.middletypeId}">${tempMiddletype.middletypeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <select name="small_type" id="small_type" style="width: 200px">
                                        <c:forEach items="${outputsmalltypeList}" var="tempSamlltype">
                                            <option value="${tempSamlltype.smalltypeId}">${tempSamlltype.smalltypeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row form-group">
                                <div class="col-md-6" id="sandbox-container">
                                    <div class="input-daterange input-group" id="datepicker" style="width: 100%">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" class="form-control" name="bgnYmd" id="bgnYmd"
                                               placeholder="開始日付" value="${outputSearchStartDate}">
                                        <span class="input-group-addon">〜</span>
                                        <input type="text" class="form-control" name="endYmd" id="endYmd"
                                               placeholder="終了日付" value="${outputSearchEndDate}">
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <input type="button" id="master-location-confirm" class="btn btn-block btn-success"
                                           onclick="searchOutputData()" value="検索">
                                </div>
                                <div class="col-md-2">
                                    <span id="outputData_num" class="outputData_num " STYLE="font-size: 16px;">合計 ${outputSearchData_num} 件</span>
                                </div>
                            </div>

                        </form>
                    </div>

                    <div class="tab-content">
                        <div class="tab-pane active" id="tab_1">
                            <table class="tablesorter table table-hover valve-table" id="outputDataList">
                                <thead>
                                <tr>
                                    <th>No</th>
                                    <th>商品名</th>
                                    <th>バーコード</th>
                                    <th>数量</th>
                                    <th>単位</th>
                                    <th>元定価</th>
                                    <th>折扣</th>
                                    <th>出庫日付</th>
                                    <th>追跡番号</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="outputData">
                                <c:forEach items="${outputLists}" var="outputList" varStatus="status">
                                    <tr id="${outputList.outputListId}">
                                        <td><c:out value="${status.count}"/></td>
                                        <td>${outputList.goodsList.goodsListName}</td>
                                        <td>${outputList.goodsList.goodsBarcode}</td>
                                        <td><input  name="outputNum-${outputList.outputListId}" id="outputNum-${outputList.outputListId}" class="tdwidth50" type="number" placeholder="数量" value="${outputList.outputNum}"/></td>
                                        <td>${outputList.goodsList.goodsUnitName}</td>
                                        <td>${outputList.goodsList.goodsPriceCH}</td>
                                        <td><input name="outputDiscount-${outputList.outputListId}" id="outputDiscount-${outputList.outputListId}" class="tdwidth50" type="number"  placeholder="折扣"  value="${outputList.outputDiscount}"/></td>
                                        <td><input name="outputDate-${outputList.outputListId}" id="outputDate-${outputList.outputListId}" class="tdwidth120" type="text"  placeholder="出庫日付"  value="${outputList.outputDate}"/></td>
                                        <td>${outputList.outputTrackNum}</td>
                                        <td>
                                            <button onclick="updateOutput(${outputList.outputListId})" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;
                                            <button onclick="deleteOutput(${outputList.outputListId})" class="btn btn-danger operation-button-btn">削除</button></td>
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
                $("#outputDataList").tablesorter({
                    headers: {
                        3: { sorter: false },
                        4: { sorter: false },
                        6: { sorter: false },
                        7: { sorter: false },
                        8: { sorter: false },
                        9: { sorter: false }
                    }
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

        //既存のキーワード設定
        //大分類
        var bigtypeSelected = "${outputSearchBigtype}";
        var objBigtype = document.getElementById("big_type");
        checkSelect(objBigtype, bigtypeSelected);

        //中分類
        var middletypeSelected = "${outputSearchMiddletype}";
        var objMiddletype = document.getElementById("middle_type");
        checkSelect(objMiddletype, middletypeSelected);
//            checkSelect(objMiddletype,"POLA");
        //小分類
        var samlltypeSelected = "${outputSearchSmalltype}";
        var objSmalltype = document.getElementById("small_type");
        checkSelect(objSmalltype, samlltypeSelected);


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
                $.post("/DataManager/type/getSmallTypeByBigType", {"outInput": "2","bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue}, function (data) {
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
            $.post("/DataManager/type/getSmallTypeByBigType", {"outInput": "2","bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue}, function (data) {
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
    function makeUpGoodsType(items) {
        $("#outputData").html("");
        var htmlContent = "";
        for (var i = 0; i < items.length; i++) {

            var outputNumId="outputNum-"+items[i].outputListId;
            var outputDiscountId="outputDiscount-"+items[i].outputListId;
            var outputDateId="outputDate-"+items[i].outputListId;

            htmlContent =
                    htmlContent + '' +
                    '<tr id="' + items[i].outputListId + '">' +
                    '<td>' + (i + 1) + '</td>' +
                    '<td>' + items[i].goodsList.goodsListName + '</td>' +
                    '<td>' + items[i].goodsList.goodsBarcode + '</td>' +
                    '<td> <input class="tdwidth50" type="number" placeholder="数量"  name="'+outputNumId+'"  id="'+outputNumId+'" value="' + items[i].outputNum + '"/></td>' +
                    '<td>' + items[i].goodsList.goodsUnitName + '</td>' +
                    '<td>' + items[i].goodsList.goodsPriceCH + '</td>' +
                    '<td> <input class="tdwidth50" type="number" placeholder="折扣"  name="'+outputDiscountId+'"  id="'+outputDiscountId+'" value="' + items[i].outputDiscount + '"/></td>' +
                    '<td> <input class="tdwidth120" type="text" placeholder="出庫日付"  name="'+outputDateId+'"  id="'+outputDateId+'" value="' + items[i].outputDate + '"/></td>' +
                    '<td>' + items[i].outputTrackNum + '</td>' +
                    '<td>' +
                    '<button onclick="updateOutput(' + items[i].outputListId + ')" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;'+
                    '<button onclick="deleteOutput(' + items[i].outputListId + ')" class="btn btn-danger operation-button-btn">削除</button>'
                    '</td>' +
                    '</tr>';
        }
        $("#outputData").html(htmlContent);
        $('#outputData_num').text('合計 '+items.length+" 件");
        $("#outputDataList").trigger("update");
    }

    //入庫検索
    function searchOutputData() {

        //検索結果リストを空にする
        $("#outputData").html("");

        //キーワード
        var bigtypeValue = $("[name=big_type]").val();
        var middletypeValue = $("[name=middle_type]").val();
        var smalltypeValue = $("[name=small_type]").val();
        var startDate = document.getElementById("bgnYmd").value;
        var endDate = document.getElementById("endYmd").value;

        console.log("Goods bigtypeValue =" + bigtypeValue);
        console.log("Goods middletypeValue =" + middletypeValue);
        console.log("Goods smalltypeValue =" + smalltypeValue);
        console.log("Goods startDate =" + startDate);
        console.log("Goods endDate =" + endDate);

        //大分類を選択しなかった場合は、検索しない
        if (bigtypeValue == "0" || middletypeValue == null || smalltypeValue == null || startDate == "" || endDate == "") {
            alert("各分類と時間を選択してから検索してください")
            return false;
        } else if (startDate > endDate) {
            alert("開始時間は終了時間より遅い")
            return false;
        } else {
            $.post("/DataManager/output/getGoodsListByKeywords", {"bigtypeValue": bigtypeValue, "middletypeValue": middletypeValue, "smalltypeValue": smalltypeValue, "startDate": startDate, "endDate": endDate}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeUpGoodsType(items);
            });
        }
    }


    //出庫修正
    function updateOutput(obj) {
        //キーワード
        console.log("outputDataId  Id="+ obj);
        var outputDataId=obj;
        var tmpOutputNum=document.getElementById("outputNum-"+outputDataId).value;
        var tmpOutputDiscount=document.getElementById("outputDiscount-"+outputDataId).value;
        var tmpOutputDate=document.getElementById("outputDate-"+outputDataId).value;

        //大分類を選択しなかった場合は、検索しない
        if (tmpOutputNum == "0" || tmpOutputNum == "") {
            alert("数量は０より大きい数字を入力してください")
            return false;
        } else if (tmpOutputDiscount<=0 || tmpOutputDiscount>100) {
            alert("割引は１－１００間の数字を入力してください")
            return false;
        } else if (isDate(tmpOutputDate)) {
            alert("日付をyyyy-mm-ddのように正しく入力してください")
            return false;
        } else {
            $.post("/DataManager/output/updateOutput", {"outputDataId": outputDataId,"tmpOutputNum": tmpOutputNum, "tmpOutputDiscount": tmpOutputDiscount, "tmpOutputDate": tmpOutputDate}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
            });
        }
    }

    //出庫削除
    function deleteOutput(obj) {
        //キーワード
        console.log("inoutDate  Id="+ obj);
        var outputDataId=obj;
        if (!confirm("この行を削除しますか？"))
            return;
        $.post("/DataManager/output/deleteOutput", {"outputDataId": outputDataId}, function (data) {
            if (data == "login") {
                logoutFun();
            }

            searchOutputData();
        });


    }

    //選択されたデータを表示する
    function checkSelect(obj, val) {
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].value == val) {
                obj[i].selected = true;
                break;
            }
        }
    }


</script>

</body>
</html>