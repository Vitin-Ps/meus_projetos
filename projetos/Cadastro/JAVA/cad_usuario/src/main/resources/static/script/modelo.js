$(document).ready(function() {
    $('#selectFunc').on('change', function() {
      var selectedValue = $(this).val();
      $.ajax({
        type: 'POST',
        url: '/enviaNomeFunc',
        data: { value: selectedValue },
        success: function(response) {
          console.log(response);
        },
        error: function(xhr, status, error) {
          console.error(error);
        }
      });
    });
  });