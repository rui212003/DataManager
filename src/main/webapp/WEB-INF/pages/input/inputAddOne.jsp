<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  User: Rui
  Date: 2018/1/30
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
            <h1>入庫追加（単体）</h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- collection -->
            <div class="col-xs-12">
                <form role="form" id="inputListForm" name="inputListForm" class="box-body-form" method="post" action="/DataManager/input/AddInputOne" >

                <div class="box box-primary">
                    <div class="box-header box-panel">
                        <h3 class="box-title">入庫追加</h3>
                    </div>


                    <div class="box-body">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    バーコード:
                                </div>
                                <div class="col-md-2">
                                    <input type="text" id="goodsBarcode" name="goodsBarcode" class="form-control" onmouseup="getBarcodeMaster()" onkeypress="getBarcodeMasterByOnkeypress(event.keyCode);" placeholder="バーコード(必須)" value="${inputAddOne.goodsList.goodsBarcode}">
                                </div>
                                <div class="col-md-2">
                                    <input type="button" class="btn btn-primary"  onclick="getBarcodeMaster()" data-toggle="modal"  id="20" value="商品名取得" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    大分類:
                                </div>
                                <div class="col-md-2">
                                    <input type="text" readonly="true" id="inputBigtypeName" name="inputBigtypeName" class="form-control" placeholder="大分類" value="${inputAddOne.inputBigtypeName}">
                                </div>
                                <div class="col-md-2" style="display: none">
                                    <input type="text" id="inputBigtypeId" name="inputBigtypeId" class="form-control" placeholder="大分類ID" value="${inputAddOne.inputBigtypeId}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    中分類:
                                </div>
                                <div class="col-md-2">
                                    <input type="text" readonly="true" id="inputMiddletypeName" name="inputMiddletypeName" class="form-control" placeholder="中分類" value="${inputAddOne.inputMiddletypeName}">
                                </div>
                                <div class="col-md-2" style="display: none">
                                    <input type="text" id="inputMiddletypeId" name="inputMiddletypeId" class="form-control" placeholder="中分類ID" value="${inputAddOne.inputMiddletypeId}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    小分類:
                                </div>
                                <div class="col-md-2">
                                    <input type="text" readonly="true" id="inputSmalltypeName" name="inputSmalltypeName" class="form-control" placeholder="小分類" value="${inputAddOne.inputSmalltypeName}">
                                </div>
                                <div class="col-md-2" style="display: none">
                                    <input type="text" id="inputSmalltypeId" name="inputSmalltypeId" class="form-control" placeholder="小分類ID" value="${inputAddOne.inputSmalltypeId}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    商品名：
                                </div>
                                <div class="col-md-2">
                                    <input type="text" readonly="true" id="goodsListName" name="goodsListName" class="form-control" placeholder="商品名" value="${inputAddOne.goodsList.goodsListName}">
                                </div>
                                <div class="col-md-2" style="display: none">
                                    <input type="text" id="goodsListId" name="goodsListId" class="form-control" placeholder="商品ID" value="${inputAddOne.goodsListId}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    数量：
                                </div>
                                <div class="col-md-2">
                                    <input type="text" id="inputNum" name="inputNum" class="form-control" placeholder="数量" value="${inputAddOne.inputNum}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    割引：
                                </div>
                                <div class="col-md-2">
                                    <input type="text" id="inputDiscount" name="inputDiscount" class="form-control" placeholder="割引" value="${inputAddOne.inputDiscount}">
                                </div>
                                <div class="col-md-3">1-100間の数字にしてください</div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    追跡番号：
                                </div>
                                <div class="col-md-2">
                                    <input type="text" id="inputTrackNum" name="inputTrackNum" class="form-control" placeholder="追跡番号" value="${inputAddOne.inputTrackNum}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    倉庫：
                                </div>
                                <div class="col-md-2">
                                <input type="text" style="display: none;" id="warehouseId" name="warehouseId" class="form-control" placeholder="倉庫" value="${inputAddOne.warehouseId}">

                                <select name="warehouse_list" id="warehouse_list" style="width: 180px">
                                    <c:forEach items="${inputAddWarehouseList}" var="tempWarehouse">
                                        <option value="${tempWarehouse.warehouseId}">${tempWarehouse.warehouseName}</option>
                                    </c:forEach>
                                </select>
                                </div>
                            </div>
                        </div>


                        <div class="box box-solid">

                            <div class="box-body clearfix">
                                <div class="form-group">

                                    <button class="btn btn-success pull-right" onclick="return checkInput()">
                                       保存
                                    </button>
                                </div>
                                <%--<input type="button" onclick="return checkInput()" value="送信" >--%>
                            </div>
                        </div>
                </div>
            </div>
                    </form>
            </div>
        </section>
        <!-- /.content -->
    </aside>
    <!-- /.right-side -->
</div>
<!-- ./wrapper -->


<script type="text/javascript">

    $(document).ready(function () {
        //The Calender
        $('#sandbox-container .input-daterange').datepicker({
            format: 'yyyy/mm/dd',
            language: 'ja',
            autoclose: true,
            todayHighlight: true
        });

        document.getElementById("warehouseId").value=$("[name=warehouse_list]").val()
    });

    $("#warehouse_list").change(function(){
        document.getElementById("warehouseId").value=$("[name=warehouse_list]").val()
   });


//    $("#goodsBarcode").mouseup(function(){
//        getBarcodeMaster();
//    });

    function getBarcodeMasterByOnkeypress(code){
        //エンターキー押下なら
        if(13 === code)
        {
            getBarcodeMaster();
        }

    }

    //入庫修正
    function updateInput(obj) {
        //キーワード
        console.log("inputDataId  Id="+ obj);
        var inputDataId=obj;
        var tmpInputNum=document.getElementById("inputNum-"+inputDataId).value;
        var tmpInputDiscount=document.getElementById("inputDiscount-"+inputDataId).value;
        var tmpInputDate=document.getElementById("inputDate-"+inputDataId).value;

        //大分類を選択しなかった場合は、検索しない
        if (tmpInputNum == "0" || tmpInputNum == "") {
            alert("数量は０より大きい数字を入力してください")
            return false;
        } else if (tmpInputDiscount<=0 || tmpInputDiscount>100) {
            alert("割引は１－１００間の数字を入力してください")
            return false;
        } else if (isDate(tmpInputDate)) {
            alert("日付をyyyy-mm-ddのように正しく入力してください")
            return false;
        } else {
            $.post("/DataManager/input/updateInput", {"inputDataId": inputDataId,"tmpInputNum": tmpInputNum, "tmpInputDiscount": tmpInputDiscount, "tmpInputDate": tmpInputDate}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
            });
        }
    }

    //入庫の必須項目チェック
    function checkInput(){
        var flag=0;

        var numTemp=$("#inputNum").val();
        var numDiscount=$("#inputDiscount").val();
        var barcodeTemp=$("#goodsBarcode").val();
        var trackNum=$("#inputTrackNum").val();



        //必須項目設定
        if(barcodeTemp==""){flag=1;}
        if(numTemp==""){flag=1;}
        if(numDiscount==""){flag=1;}
        if(trackNum==""){flag=1;}

        //長さ制限の項目設定

        var tmpVNo=$("#VNo").val();
        if(numTemp<1){
            window.alert("「数量」を入力ください");
            return false;
        }
        if(numDiscount<0 && numDiscount>100){
            window.alert("「割引」を１－１００間の数字を入力ください");
            return false;
        }

        document.inputListForm.submit()
    }


    function getBarcodeMaster(){

        var inputBarcode=  document.getElementById("goodsBarcode").value;

        console.log("temp barcode="+inputBarcode);
        if(inputBarcode==""){
            document.getElementById("inputBigtypeName").value="";
            document.getElementById("inputBigtypeId").value="";
            document.getElementById("inputMiddletypeName").value="";
            document.getElementById("inputMiddletypeId").value="";
            document.getElementById("inputSmalltypeName").value="";
            document.getElementById("inputSmalltypeId").value="";
            document.getElementById("goodsListName").value="";
            document.getElementById("goodsListId").value="";
            document.getElementById("inputNum").value="";
            document.getElementById("inputDiscount").value="";
            document.getElementById("inputTrackNum").value="";

//            alert("バーコードを入力してください");
        }else{
            console.log("temp barcode else="+inputBarcode);
            $.post("/DataManager/goodsList/getGoodListByBarcode", {"inputBarcode": inputBarcode}, function (data) {
                if (data == "login") {
                    alert("バーコードから商品分類できない、ご確認ください");
                }else{
                    var items = JSON.parse(data);
                    document.getElementById("inputBigtypeName").value=items.inputBigtypeName;
                    document.getElementById("inputBigtypeId").value=items.inputBigtypeId;
                    document.getElementById("inputMiddletypeName").value=items.inputMiddletypeName;
                    document.getElementById("inputMiddletypeId").value=items.inputMiddletypeId;
                    document.getElementById("inputSmalltypeName").value=items.inputSmalltypeName;
                    document.getElementById("inputSmalltypeId").value=items.inputSmalltypeId;
                    document.getElementById("goodsListName").value=items.goodsList.goodsListName;
                    document.getElementById("goodsListId").value=items.goodsListId;
                }
            });
        }


    }

</script>

</body>
</html>