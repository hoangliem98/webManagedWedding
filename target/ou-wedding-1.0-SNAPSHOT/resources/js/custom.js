/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/////////////loading
$(window).on('load', function (event) {
    setTimeout(function () {
        $('body').removeClass('loading-active');
    }, 1500);
});
$(document).ready(function () {
    //////////upload-img
    $('.input-img').change(function () {
        let reader = new FileReader();
        reader.onload = (e) => {
            $file = reader.result;
            if ($file.indexOf('image/jpeg') > 0 || $file.indexOf('image/png') > 0) {
                $('.image_preview_container').attr('src', e.target.result);
            } else {
                alert('Sai định dạng. Chỉ được thêm hình ảnh');
                $('.image_preview_container').attr('src', '/ou-wedding/javax.faces.resource/preview.jpg.xhtml?ln=images');
                $('.image_upload').val('');
                console.log($('.input-img').val());
            }
        }
        reader.readAsDataURL(this.files[0]);
    });
    /////set delay time
    var delay = (function () {
        var timer = 0;
        return function (callback, ms) {
            clearTimeout(timer);
            timer = setTimeout(callback, ms);
        };
    })();
    //////minimum && maximum
    $('.maximum').keyup(function () {
        var minimum = $('.minimum').val();
        var maximum = $('.maximum').val();
        if (maximum < minimum && maximum !== "") {
            delay(function () {
                alert('Số bạn tối đa không được nhỏ hơn số bàn tối thiểu !!!');
                $('.maximum').val('');
            }, 500);
        }
    });

    ////change password
    $(".changepass").change(function () {
        if ($(this).is(":checked"))
        {
            $(".password").removeAttr('readonly');
        } else
        {
            $(".password").attr('readonly', '');
        }
    });

    ////date picker
    $("#datepicker").datepicker({
        prevText: "Trước",
        nextText: "Sau",
        currentText: "Hôm nay",
        monthNames: ["Tháng một", "Tháng hai", "Tháng ba", "Tháng tư", "Tháng năm", "Tháng sáu", "Tháng bảy", "Tháng tám", "Tháng chín", "Tháng mười", "Tháng mười một", "Tháng mười hai"],
        monthNamesShort: ["Một", "Hai", "Ba", "Bốn", "Năm", "Sáu", "Bảy", "Tám", "Chín", "Mười", "Mười một", "Mười hai"],
        dayNames: ["Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"],
        dayNamesShort: ["CN", "Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy"],
        dayNamesMin: ["CN", "T2", "T3", "T4", "T5", "T6", "T7"],
        weekHeader: "Tuần",
        firstDay: 1,
        showMonthAfterYear: false,
        autoclose: true,
        format: 'yyyy-mm-dd',
        todayHighlight: true
    });
});

//////delete
function deleteSuccess() {
    swal(
            "Xóa thành công",
            "Dữ liệu đã được xóa",
            "success"
            ).then(function () {
        window.location.reload();
    });
}
function deleteError() {
    swal(
            "Xóa thất bại",
            "Dữ liệu chưa được xóa",
            "error"
            ).then(function () {
        window.location.reload();
    });
}



