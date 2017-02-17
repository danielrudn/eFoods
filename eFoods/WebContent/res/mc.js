function addToCart(item) {
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/eFoods/cart?add=' + item);
	xhr.send(null);
	var cartCount = document.getElementById('cart-count');
	cartCount.classList.add('cart-count-glow');
	setTimeout(function() {
		cartCount.classList.remove('cart-count-glow');
	}, 500);
	cartCount.innerHTML = parseInt(cartCount.innerHTML) + 1;
}

function onMaxPriceChange(element) {
	var currentVal = document.getElementById("currentMaxPrice");
	currentVal.innerHTML = ' $' + element.value;
}