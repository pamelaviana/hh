document.querySelector('#search').addEventListener('keyup', function(e){
    var searchValue = e.target.value.toLowerCase();
    var rows = document.querySelectorAll('#dataTable tbody tr');

    for(var i=0; i<rows.length; i++){
        var name = rows[i].querySelector('td').textContent.toLowerCase();

        if(name.indexOf(searchValue) > -1){
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
});