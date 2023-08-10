 import {
        API_URL_INSERT_CARD,
        API_URL_DELETE_CARD, API_URL_GET_ALL_CARDS,
        API_URL_GET_SINGLE_CARD, API_URL_UPDATE_CARD,
        HTTP_METHOD_GET_NO_CACHE, HTTP_REQUEST_NO_CACHE
    } from "./api-urls.js"

    let todoContainer = document.getElementById("todoContainer")
    let inProgressContainer = document.getElementById("inProgressContainer")
    let doneContainer = document.getElementById("doneContainer")

    let taskCardTemplate = document.getElementById("taskCardTemplate")

    let myModal = new bootstrap.Modal(document.getElementById('modalUpdate'));

    // Use this line to select the textarea elements
    let modalAdd_textareaDescription = document.getElementById("modalAdd_textareaDescription");
    let inputDescription = document.getElementById("inputDescription");

    let _jsonResponse = []



    fetchAllTasks()

    async function fetchAllTasks() {

        // fetch response using API URL and HTTP method
        const response = await fetch(API_URL_GET_ALL_CARDS, HTTP_METHOD_GET_NO_CACHE)

        // if error display error massege
        if (!response.ok) {
            throw new Error(`HTTP error! status : ${response.status}`)
        }

        // extract json from the response
        _jsonResponse = await response.json()

        // console.log(JSON.stringify(jsonResponse))

        contentAdapter()

    }



    function contentAdapter() {

        _jsonResponse.forEach(task => {
            addTaskCard(task)
        })

    }


    function addTaskCard(task) {
    console.log("Adding task card:", task);
        let clone = taskCardTemplate.content.cloneNode(true);

        let taskCardId = clone.getElementById("taskCardId");
        let taskCardEditButton = clone.getElementById("taskCardEditButton");
        let taskCardTitle = clone.getElementById("taskCardTitle");
        let taskCardDescription = clone.getElementById("taskCardDescription");

        taskCardId.innerText = "#" + task.card_id;
        taskCardTitle.innerText = task.title;
        taskCardDescription.innerHTML = task.description; // Set the description here

        taskCardEditButton.addEventListener("click", function () {
            myModal.show();
            fetchSingleTask(task.card_id);
        });

        switch (task.section) {
            case 1: todoContainer.appendChild(clone); break;
            case 2: inProgressContainer.appendChild(clone); break;
            case 3: doneContainer.appendChild(clone); break;
        }
    }



    async function fetchSingleTask(card_id) {

        let url = API_URL_GET_SINGLE_CARD + card_id
        // fetch response using API URL and HTTP method
        const response = await fetch(url, HTTP_METHOD_GET_NO_CACHE)

        // if error display error massege
        if (!response.ok) {
            throw new Error(`HTTP error! status : ${response.status}`)
        }

        // extract json from the response
        let jsonResponse = await response.json()

        // console.log(JSON.stringify(jsonResponse))

        modalContentAdapter(jsonResponse)

    }


   function modalContentAdapter(jsonResponse) {
       console.log(jsonResponse);
       let selectStatus = document.getElementById("selectStatus");
       let inputTitle = document.getElementById("inputTitle");
       let inputDescription = document.getElementById("inputDescription");

       if (inputDescription) {
           inputDescription.value = jsonResponse.description;
       }

       let btnDelete = document.getElementById("btnDelete");
       let btnSubmit = document.getElementById("btnSubmit");

       selectStatus.value = jsonResponse.section;
       inputTitle.value = jsonResponse.title;
       inputDescription.value = jsonResponse.description; // Use value here, not innerText

       btnDelete.onclick = () => {
           if (confirm("Are you sure you want to delete " + jsonResponse.title + "?")) {
               fetchDeleteTask(jsonResponse.card_id);
           }
       };

       btnSubmit.onclick = () => {
           if (confirm("Are you sure you want to update " + jsonResponse.title + "?")) {
               let updatedObject = jsonResponse;
               updatedObject.section = selectStatus.value;
               updatedObject.title = inputTitle.value;
               updatedObject.description = inputDescription.value; // Use value here, not innerText

               fetchUpdateTask(updatedObject);
           }
       };
   }









    //////////////////////////////
    async function fetchUpdateTask(updatedObject) {
        let url = API_URL_UPDATE_CARD + updatedObject.card_id

        // fetch response using API URL and HTTP method
        const response = await fetch(url, HTTP_REQUEST_NO_CACHE('PUT', updatedObject))

        // if error display error massege
        if (!response.ok) {
            throw new Error(`HTTP error! status : ${response.status}`)
        }

        // extract json from the response
        let jsonResponse = await response.json()

        // console.log(JSON.stringify(jsonResponse))

        myModal.hide();
        window.location.reload();
    }


    async function fetchDeleteTask(card_id) {
        let url = API_URL_DELETE_CARD + card_id

        // fetch response using API URL and HTTP method
        const response = await fetch(url, HTTP_REQUEST_NO_CACHE('DELETE', ''))

        // if error display error massege
        if (!response.ok) {
            throw new Error(`HTTP error! status : ${response.status}`)
        }

        // extract json from the response
        let jsonResponse = await response.json()

        // console.log(JSON.stringify(jsonResponse))

        myModal.hide();
        window.location.reload();
    }




    let btnModalAdd = document.getElementById("modalAdd_btnSubmit");

    btnModalAdd.onclick = function () {
        if (confirm("Are you sure you want to add this item?")) {
            let modalAdd_selectStatus = document.getElementById("modalAdd_selectStatus");
            let modalAdd_inputTitle = document.getElementById("modalAdd_inputTitle");
            let modalAdd_inputDescription = document.getElementById("modalAdd_textareaDescription");


            console.log("modalAdd_selectStatus:", modalAdd_selectStatus); // Debugging
            console.log("modalAdd_inputTitle:", modalAdd_inputTitle); // Debugging
            console.log("modalAdd_inputDescription:", modalAdd_inputDescription); // Debugging

            let taskObject = {
                title: modalAdd_inputTitle.value,
                description: modalAdd_inputDescription.value,
                section: modalAdd_selectStatus.value
            };

            console.log(JSON.stringify(taskObject));

            fetchInsertTask(taskObject);
        }
    };


    async function fetchInsertTask(taskObject) {

        // fetch response using API URL and HTTP method
        const response = await fetch(API_URL_INSERT_CARD, HTTP_REQUEST_NO_CACHE('POST', taskObject))

        // if error display error massege
        if (!response.ok) {
            throw new Error(`HTTP error! status : ${response.status}`)
        }

        // extract json from the response
        let jsonResponse = await response.json()

        // console.log(JSON.stringify(jsonResponse))

        myModal.hide();


        window.location.reload();
        // $( "#your_div_ID" ).load(window.location.href + " #your_div_ID" );

    }