/**
 * 비밀번호 문자로 보이게, 별표로 보이게 하는 함수
 * @param button
 * @param input
 */
export function togglePassword (button, input) {
  let isShow = false;

  button.addEventListener('click', function() {
    isShow = !isShow;
    if(isShow) {
      button.classList.add('fa-eye-slash')
      input.type = 'text';
    } else {
      button.classList.remove('fa-eye-slash')
      input.type = 'password';
    }
  })

}

