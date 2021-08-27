function onLoad(event) {
    fetch('http://localhost:7000/currentuser', {
        'credentials': 'include',
        'method': 'GET'
    }).then((response) => {
        if (response.status === 401) {
            window.location.href = '/index.html'
        } else if (response.status === 200) {
            return response.json();
        }
    }).then((user) => {
        return fetch(`http://localhost:7000/user/${user.id}/reimbursement`, {
            'method': 'GET', 
            'credentials': 'include'
        });
    }).then((response) => {
        return response.json()
    }).then((reimbursements) => {
        populateReimbursements(reimbursements);
    })
}

function populateReimbursements(reimbursementArray) {
    let tbody = document.querySelector('#reimbursements tbody');

    for (const reimbursement of reimbursementArray) {
        /*
        <th>Ship ID</th>
                    <th>Ship Name</th>
                    <th>Ship Age</th>
                    <th>Ship Owner First Name</th>
                    <th>Ship Owner Last Name</th>
                    <th>Ship Status</th>
        */

        let tr = document.createElement('tr');

        let reimbursementIdTd = document.createElement('td');
        reimbursementIdTd.innerHTML = reimbursement.id;

        let reimbursementStatus = document.createElement('td');
        reimbursementStatus.innerHTML = reimbursement.status.status;

        let reimbursementAmount = document.createElement('td');
        reimbursementAmount.innerHTML = reimbursement.amount;

        tr.appendChild(reimbursementIdTd);
        tr.appendChild(reimbursementStatus);
        tr.appendChild(reimbursementAmount);

        tbody.appendChild(tr);
    }
}

window.addEventListener('load', onLoad);