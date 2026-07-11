document.querySelector('#login-form').addEventListener('submit', (e) => {
  e.preventDefault();
  const btn = e.target.querySelector('.btn-primary');
  const originalText = btn.textContent;
  btn.textContent = 'Signing in...';
  btn.style.opacity = '0.8';
  
  // Simulate API call
  setTimeout(() => {
    btn.textContent = 'Success!';
    btn.style.background = 'linear-gradient(135deg, #10b981 0%, #059669 100%)';
    setTimeout(() => {
      btn.textContent = originalText;
      btn.style.background = '';
      btn.style.opacity = '1';
    }, 2000);
  }, 1500);
});
