async function submitMember(event) {
    event.preventDefault();

    const alertBox = document.getElementById('alertBox');
    const form = document.getElementById('memberForm');

    const formData = new FormData(form);

    const memberData = {};
    formData.forEach((value, key) => {
        memberData[key] = value;
    });

    try {
        const response = await fetch('/api/member/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(memberData),
        });

        if (response.ok) {
            alertBox.innerHTML = `<div class="alert success">회원가입이 성공적으로 완료되었습니다.</div>`;
            form.reset();
        } else {
            const errorText = await response.text();
            throw new Error(errorText || '회원가입 중 오류가 발생했습니다.');
        }
    } catch (error) {
        alertBox.innerHTML = `<div class="alert error">오류: ${error.message}</div>`;
        console.error('Fetch error:', error);
    }
}