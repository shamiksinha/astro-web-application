/**
 * 
 */
jQuery(function() {
	$("#rashiModal").on('show.bs.modal', function(e) {
		var rashiModalDiv = $(this);
		var rashi = $(e.relatedTarget) // Button that triggered the modal
		var rashiName = 'div#rashi-' + rashi.data('name') // Extract info from
		// data-* attributes
		rashiModalDiv.find(".modal-body " + rashiName).show();
	});

	$("#rashiModal").on('hide.bs.modal', function(e) {
		var rashiModalDiv = $(this);
		var rashiName = "div[id^='rashi-']"; // Extract info from data-*
		// attributes
		rashiModalDiv.find(".modal-body " + rashiName).hide();
	});

	$('#startTime').on(
			'change',
			function(e) {
				// alert($(this).val());
				console.log($("#mirror_field").val());
				var selectedTime = $(this).val();
				var endDateTime = dateAdd(selectedTime, 'minute', 15);
				var endDateTimeYear = endDateTime.getFullYear();
				var endDateTimeMonth = endDateTime.getMonth() + 1;
				if (endDateTimeMonth < 10) {
					endDateTimeMonth = '0' + endDateTimeMonth;
				}
				var endDateTimeDate = endDateTime.getDate();
				if (endDateTimeDate < 10) {
					endDateTimeDate = '0' + endDateTimeDate
				}
				var endDateTimeHours = endDateTime.getHours();
				if (endDateTimeHours < 10) {
					endDateTimeHours = '0' + endDateTimeHours;
				}
				var endDateTimeMinutes = endDateTime.getMinutes();
				if (endDateTimeMinutes < 10) {
					endDateTimeMinutes = '0' + endDateTimeMinutes;
				}
				$('#endTime').val(
						endDateTimeYear + '-' + endDateTimeMonth + '-'
								+ endDateTimeDate + ' ' + endDateTimeHours
								+ ':' + endDateTimeMinutes);
			});

	/*
	 * $('#productDiv .btn').on('click', function(e) {
	 * console.log($(this).data('item')); $("input[id*='product']").val("");
	 * $("input[id*='product']").val($(this).data('item')); return true; });
	 */

	$('#bookingModal').on('show.bs.modal', function(e) {
		var options = {
			minDate : moment({hour:21,minute:0}),
			stepping : 20,
			showTodayButton : true,
			daysOfWeekDisabled : [ 5, 6 ],
			format : "YYYY-MM-DD HH:mm",
			enabledHours : [ 21, 22 ],
			ignoreReadonly : true
		}
		var bookingModal = $(this);
		var booking = $(e.relatedTarget);
		var bookingDesc = booking.data('bookingdesc');
		var bookingType = booking.data('bookingtype');
		console.log("bookingType on show " + bookingType)
		bookingModal.find("input#bookingType").val(bookingType);
		bookingModal.find("input#bookingDesc").val(bookingDesc);
		console.log(bookingModal.find("input#bookingType").val());
		console.log(bookingModal.find("input#bookingDesc").val());

		var timeBooked=15;
		var amount=0;
		if (bookingType === 'paid') {
			bookingModal.find('div#amountDiv').show();
			options = {
				minDate : moment({hour:11,minute:0}),
				stepping : 70,
				showTodayButton : true,
				daysOfWeekDisabled : [ 5, 6 ],
				format : "YYYY-MM-DD HH:mm",
				//disabledHours : [ 21, 22 ,23],
				enabledHours : [ 11, 12, 13, 14, 15, 16, 17, 18 ],
				ignoreReadonly : true
			}
			timeBooked=60;
			amount=100;
		}
		/*options.useCurrent = false;
		bookingModal.find('div#enddatetimepicker').datetimepicker(options);
		bookingModal.find('div#enddatetimepicker').data("DateTimePicker").disable();
		options.useCurrent = true;*/
		bookingModal.find('div#startdatetimepicker').datetimepicker(options);
		var selectedDate=bookingModal.find('div#startdatetimepicker').data('datetimepicker').date();		
		$('#bookingModal div#enddatetimepicker input#endTime').val(selectedDate.add(timeBooked,'m').format("YYYY-MM-DD HH:mm"));
		bookingModal.find('div#amountDiv input#amount').val(amount);
	});

	$('#bookingModal').on('hide.bs.modal', function(e) {
		var bookingModal = $(this);
		//if (bookingType === 'paid') {
            bookingModal.find('div#amountDiv').hide();
            bookingModal.find('div#startdatetimepicker').data('datetimepicker').date(null);
            bookingModal.find('div#amountDiv input#amount').val(0);
		//}
	});

	$("#bookingModal div#startdatetimepicker").on(
			"change.datetimepicker",
			function(e) {
                if (e.date==null){
                    $('#bookingModal div#enddatetimepicker input#endTime').val("");
                } else if (e.oldDate != null) {
					/*$('#bookingModal div#enddatetimepicker').data(
							"DateTimePicker").minDate(e.date);*/
					if (bookingType === 'paid') {
						$('#bookingModal div#enddatetimepicker input#endTime').val(e.date.add(60, 'm').format("YYYY-MM-DD HH:mm"));
					} else {
						$('#bookingModal div#enddatetimepicker input#endTime').val(e.date.add(15, 'm').format("YYYY-MM-DD HH:mm"));
					}
                }
			});
	/*
	 * $("#bookingModal div#enddatetimepicker").on("dp.change", function (e) {
	 * $('#bookingModal
	 * div#startdatetimepicker').data("DateTimePicker").maxDate(e.date); if
	 * (bookingType==='paid'){ $('#bookingModal
	 * div#startdatetimepicker').data("DateTimePicker").setValue($(this).viewDate().subtract(60,'m'));
	 * }else{
	 *  }
	 * 
	 * });
	 */

	$('#productModal').on('show.bs.modal', function(e) {
		var productModalDiv = $(this);
		var productSelected = $(e.relatedTarget).data('item');
		productModalDiv.find('input#product').val(productSelected);
	});

	$('#productModal').on('hide.bs.modal', function(e) {
		var productModalDiv = $(this);
		productModalDiv.find('input#product').val("");
	});

	/**
	 * Adds time to a date. Modelled after MySQL DATE_ADD function. Example:
	 * dateAdd(new Date(), 'minute', 30) //returns 30 minutes from now.
	 * https://stackoverflow.com/a/1214753/18511
	 * 
	 * @param date
	 *            Date to start with
	 * @param interval
	 *            One of: year, quarter, month, week, day, hour, minute, second
	 * @param units
	 *            Number of units of the given interval to add.
	 */
	function dateAdd(date, interval, units) {
		var ret = new Date(date); // don't change original date
		var checkRollover = function() {
			if (ret.getDate() != date.getDate())
				ret.setDate(0);
		};
		switch (interval.toLowerCase()) {
		case 'year':
			ret.setFullYear(ret.getFullYear() + units);
			checkRollover();
			break;
		case 'quarter':
			ret.setMonth(ret.getMonth() + 3 * units);
			checkRollover();
			break;
		case 'month':
			ret.setMonth(ret.getMonth() + units);
			checkRollover();
			break;
		case 'week':
			ret.setDate(ret.getDate() + 7 * units);
			break;
		case 'day':
			ret.setDate(ret.getDate() + units);
			break;
		case 'hour':
			ret.setTime(ret.getTime() + units * 3600000);
			break;
		case 'minute':
			ret.setTime(ret.getTime() + units * 60000);
			break;
		case 'second':
			ret.setTime(ret.getTime() + units * 1000);
			break;
		default:
			ret = undefined;
			break;
		}
		return ret;
	}
});