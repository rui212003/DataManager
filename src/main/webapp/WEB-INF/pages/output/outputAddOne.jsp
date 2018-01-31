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
            <h1>出庫追加（単体）</h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- collection -->
            <div class="col-xs-12">
                <form role="form" id="outputListForm" name="outputListForm" class="box-body-form" method="post" action="/DataManager/output/AddOutputOne" >

                <div class="box box-primary">
                    <div class="box-header box-panel">
                        <h3 class="box-title">出庫追加</h3>
                    </div>


                    <div class="box-body">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    バーコード:
                                </div>
                                <div class="col-md-2">
                                    <input type="text" id="goodsBarcode" name="goodsBarcode" class="form-control" onmouseup="getBarcodeMaster()" onkeypress="getBarcodeMasterByOnkeypress(event.keyCode);" placeholder="バーコード(必須)" value="${outputAddOne.goodsList.goodsBarcode}">
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
                                    <input type="text" readonly="true" id="outputBigtypeName" name="outputBigtypeName" class="form-control" placeholder="大分類" value="${outputAddOne.outputBigtypeName}">
                                </div>
                                <div class="col-md-2" style="display: none">
                                    <input type="text" id="outputBigtypeId" name="outputBigtypeId" class="form-control" placeholder="大分類ID" value="${outputAddOne.outputBigtypeId}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    中分類:
                                </div>
                                <div class="col-md-2">
                                    <input type="text" readonly="true" id="outputMiddletypeName" name="outputMiddletypeName" class="form-control" placeholder="中分類" value="${outputAddOne.outputMiddletypeName}">
                                </div>
                                <div class="col-md-2" style="display: none">
                                    <input type="text" id="outputMiddletypeId" name="outputMiddletypeId" class="form-control" placeholder="中分類ID" value="${outputAddOne.outputMiddletypeId}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    小分類:
                                </div>
                                <div class="col-md-2">
                                    <input type="text" readonly="true" id="outputSmalltypeName" name="outputSmalltypeName" class="form-control" placeholder="小分類" value="${outputAddOne.outputSmalltypeName}">
                                </div>
                                <div class="col-md-2" style="display: none">
                                    <input type="text" id="outputSmalltypeId" name="outputSmalltypeId" class="form-control" placeholder="小分類ID" value="${outputAddOne.outputSmalltypeId}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    商品名：
                                </div>
                                <div class="col-md-2">
                                    <input type="text" readonly="true" id="goodsListName" name="goodsListName" class="form-control" placeholder="商品名" value="${outputAddOne.goodsList.goodsListName}">
                                </div>
                                <div class="col-md-2" style="display: none">
                                    <input type="text" id="goodsListId" name="goodsListId" class="form-control" placeholder="商品ID" value="${outputAddOne.goodsListId}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    数量：
                                </div>
                                <div class="col-md-2">
                                    <input type="text" id="outputNum" name="outputNum" class="form-control" placeholder="数量" value="${outputAddOne.outputNum}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    割引：
                                </div>
                                <div class="col-md-2">
                                    <input type="text" id="outputDiscount" name="outputDiscount" class="form-control" placeholder="割引" value="${outputAddOne.outputDiscount}">
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
                                    <input type="text" id="outputTrackNum" name="outputTrackNum" class="form-control" placeholder="追跡番号" value="${outputAddOne.outputTrackNum}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-1">
                                    倉庫：
                                </div>
                                <div class="col-md-2">
                                <input type="text" style="display: none;" id="warehouseId" name="warehouseId" class="form-control" placeholder="倉庫" value="${outputAddOne.warehouseId}">

                                <select name="warehouse_list" id="warehouse_list" style="width: 180px">
                                    <c:forEach items="${outputAddWarehouseList}" var="tempWarehouse">
                                        <option value="${tempWarehouse.warehouseId}">${tempWarehouse.warehouseName}</option>
                                    </c:forEach>
                                </select>
                                </div>
                            </div>
                        </div>


                        <div class="box box-solid">

                            <div class="box-body clearfix">
                                <div class="form-group">

                                    <button class="btn btn-success pull-right" onclick="return checkOutput()">
                                       保存
                                    </button>
                                </div>
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


    function getBarcodeMasterByOnkeypress(code){
        //エンターキー押下なら
        if(13 === code)
        {
            getBarcodeMaster();
        }

    }



    //入庫の必須項目チェック
    function checkOutput(){
        var flag=0;

        var numTemp=$("#outputNum").val();
        var numDiscount=$("#outputDiscount").val();
        var barcodeTemp=$("#goodsBarcode").val();
        var trackNum=$("#outputTrackNum").val();



        //必須項目設定
        if(barcodeTemp==""){flag=1;}
        if(numTemp==""){flag=1;}
        if(numDiscount==""){flag=1;}
        if(trackNum==""){flag=1;}

        //長さ制限の項目設定

        if(numTemp<1){
            window.alert("「数量」を入力ください");
            return false;
        }
        if(numDiscount<0 && numDiscount>100){
            window.alert("「割引」を１－１００間の数字を入力ください");
            return false;
        }

        document.outputListForm.submit()
    }


    function getBarcodeMaster(){

        var outputBarcode=  document.getElementById("goodsBarcode").value;

        console.log("temp outputBarcode="+outputBarcode);
        if(outputBarcode==""){
            document.getElementById("outputBigtypeName").value="";
            document.getElementById("outputBigtypeId").value="";
            document.getElementById("outputMiddletypeName").value="";
            document.getElementById("outputMiddletypeId").value="";
            document.getElementById("outputSmalltypeName").value="";
            document.getElementById("outputSmalltypeId").value="";
            document.getElementById("goodsListName").value="";
            document.getElementById("goodsListId").value="";
            document.getElementById("outputNum").value="";
            document.getElementById("outputDiscount").value="";
            document.getElementById("outputTrackNum").value="";

//            alert("バーコードを入力してください");
        }else{
            console.log("temp barcode else="+outputBarcode);
            $.post("/DataManager/goodsList/getGoodListByBarcodeOutput", {"outputBarcode": outputBarcode}, function (data) {
                if (data == "login") {
                    alert("バーコードから商品分類が取得できない、ご確認ください");
                }else{
                    var items = JSON.parse(data);
                    document.getElementById("outputBigtypeName").value=items.outputBigtypeName;
                    document.getElementById("outputBigtypeId").value=items.outputBigtypeId;
                    document.getElementById("outputMiddletypeName").value=items.outputMiddletypeName;
                    document.getElementById("outputMiddletypeId").value=items.outputMiddletypeId;
                    document.getElementById("outputSmalltypeName").value=items.outputSmalltypeName;
                    document.getElementById("outputSmalltypeId").value=items.outputSmalltypeId;
                    document.getElementById("goodsListName").value=items.goodsList.goodsListName;
                    document.getElementById("goodsListId").value=items.goodsListId;
                }
            });
        }


    }

</script>

</body>
</html>