
const API_BASE_URL = "http://localhost:8080/api";
async  function fetchBoardData(){
    const loading = document.getElementById("loading");
    const error = document.getElementById("error");
    const table = document.getElementById("boardTable");
    const tbody = document.getElementById("boardBody");


    loading.style.display = "block";
    error.style.display = "none";
    table.style.display = "none";
    tbody.innerHTML = "";


    const res = await fetch(API_BASE_URL + "/board/all")

    // ok = 200 200이 아닌게 맞을 때
    if(!res.ok) {
        throw new Error("서버 응답 오류 : " + res.status);
    }
    const board = await  res.json();

    if(board.length === 0) {
        tbody.innerHTML=`<tr><td colspan="5">게시글이 없습니다.</td></tr>`;
    } else {
        board.forEach(
            b => {
                const row = document.createElement("tr");

                row.innerHTML=`
<td>${b.id}</td>
                <td class="title-cell" onclick="openModal(${b.id})">${b.title}</td>
                <td>${b.writer}</td>
                
                `;
                tbody.appendChild(row);
            });
    }

    table.style.display = "table";


}

// 게시글 상세보기 (모달열기)
async function openModal(id) {
    const modal = document.getElementById("viewModal");
    const modalTitle = document.getElementById("modalTitle");
    const modalInfo = document.getElementById("modalInfo");
    const modalContent = document.getElementById("modalContent");


    modal.style.display ="flex";

    const res = await  fetch(API_BASE_URL + `/board/${id}`);

    const board = await  res.json();

    modalTitle.textContent = board.title;
    modalInfo.textContent = `작성자 ${board.writer}`;
    modalContent.textContent = board.content;
}

function  closeModal(){
    const modal = document.getElementById("viewModal");
    modal.style.display = "none";
}

// 페이지 로드 시 자동으로 특정 기능 실행
window.addEventListener("DOMContentLoaded", fetchBoardData);