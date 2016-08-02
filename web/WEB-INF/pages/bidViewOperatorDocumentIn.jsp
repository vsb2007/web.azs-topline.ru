<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authorize access="hasRole('ROLE_BID_UPDATE')">
    <div class="grid-list">
        <div class="tile">
            <select class="dropdown-menu" id="" name="destinationCompany">
                <option value="1">Топлайн</option>
                <option value="2">Управление АЗС</option>
                <option value="3">Другой</option>
            </select>
            <div>
                <span class="secondary-text">Грузополучатель</span>
            </div>
        </div>
        <div class="tile">
            <select class="dropdown-menu" name="destinationCompany">
                <option value="-1">Выбырете договор</option>
                <option value="2">Договор 1</option>
                <option value="3">Договор 2</option>
            </select>
            <div>
                <span class="secondary-text">Основание</span>
            </div>
        </div>
        <br>
        Товарная накладная<br>
        <div class="tile">
            <input type="text" class="text-input border-green-500" placeholder="Номер" required
                   name="docNumber" id="docNumber" pattern="^[0-9]{3}-[0-9]{3}-[0-9]{8}$">
            <div>
                <span class="secondary-text">Номер документа</span>
            </div>
        </div>
        &nbsp;&nbsp;&nbsp;
        <div class="tile">
            <input type="text" class="text-input border-green-500" placeholder="Дата" required
                   name="docData" id="docData" readonly>
            <div>
                <span class="secondary-text">Дата составления</span>
            </div>
        </div>
        <br>
        Доверенность<br>
        <div class="tile">
            <input type="text" class="text-input border-green-500" placeholder="Номер" required
                   name="doverNumber">
            <div>
                <span class="secondary-text">Номер доверенности</span>
            </div>
        </div>
        &nbsp;&nbsp;&nbsp;
        <div class="tile">
            <input type="text" class="text-input border-green-500" placeholder="Дата" required
                   name="doverData" id="doverData" readonly>
            <div>
                <span class="secondary-text">Дата составления</span>
            </div>
        </div>
    </div>
    <script src="js/jquery.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script>
        $.datepicker.regional['ru'] = {
            closeText: 'Закрыть',
            prevText: '&#x3c;Пред',
            nextText: 'След&#x3e;',
            currentText: 'Сегодня',
            monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
                'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
            monthNamesShort: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
                'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
            dayNames: ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'],
            dayNamesShort: ['вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт'],
            dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
            weekHeader: 'Нед',
            dateFormat: 'dd.mm.yy',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: ''
        };
        $.datepicker.setDefaults(
                $.extend($.datepicker.regional["ru"])
        );
        $("#docData").datepicker({
                    defaultDate: "",
                    changeMonth: false,
                    numberOfMonths: 1,
                    dateFormat: 'dd.mm.yy', // See format options on parseDate
                    firstDay: 1
                }
        );
        $("#doverData").datepicker({
            defaultDate: "",
            changeMonth: false,
            numberOfMonths: 1,
            dateFormat: 'dd.mm.yy', // See format options on parseDate
            firstDay: 1
        });

        /**
         * charCode [48,57]    Numbers 0 to 9
         * keyCode 46            "delete"
         * keyCode 9            "tab"
         * keyCode 13            "enter"
         * keyCode 116            "F5"
         * keyCode 8            "backscape"
         * keyCode 37,38,39,40    Arrows
         * keyCode 10            (LF)
         */
        function validate_int(myEvento) {
            if ((myEvento.charCode >= 48 && myEvento.charCode <= 57) || myEvento.keyCode == 9 || myEvento.keyCode == 10 || myEvento.keyCode == 13 || myEvento.keyCode == 8 || myEvento.keyCode == 116 || myEvento.keyCode == 46 || (myEvento.keyCode <= 40 && myEvento.keyCode >= 37)) {
                dato = true;
            } else {
                dato = false;
            }
            return dato;
        }

        function phone_number_mask() {
            var myMask = "___-___-________";
            var myCaja = document.getElementById("docNumber");
            var myText = "";
            var myNumbers = [];
            var myOutPut = ""
            var theLastPos = 1;
            myText = myCaja.value;
            //get numbers
            for (var i = 0; i < myText.length; i++) {
                if (!isNaN(myText.charAt(i)) && myText.charAt(i) != " ") {
                    myNumbers.push(myText.charAt(i));
                }
            }
            //write over mask
            for (var j = 0; j < myMask.length; j++) {
                if (myMask.charAt(j) == "_") { //replace "_" by a number
                    if (myNumbers.length == 0)
                        myOutPut = myOutPut + myMask.charAt(j);
                    else {
                        myOutPut = myOutPut + myNumbers.shift();
                        theLastPos = j + 1; //set caret position
                    }
                } else {
                    myOutPut = myOutPut + myMask.charAt(j);
                }
            }
            document.getElementById("docNumber").value = myOutPut;
            document.getElementById("docNumber").setSelectionRange(theLastPos, theLastPos);
        }
        document.getElementById("docNumber").onkeypress = validate_int;
        document.getElementById("docNumber").onkeyup = phone_number_mask;
    </script>
</sec:authorize>

