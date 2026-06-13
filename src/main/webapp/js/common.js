/**
 *  Helper functions
 */

/* ---------- DOM ---------- */
function getById(id) {
    return document.getElementById(id);
}

// Keep a dedicated helper alias without overriding jQuery's global `$`.
var $id = getById;

// Backward compatibility: only expose `$` if jQuery is not present.
if (typeof window !== "undefined" && typeof window.$ !== "function") {
    window.$ = getById;
}

/* ---------- PRELOADER SAFETY ---------- */
(function () {
    if (typeof document === "undefined") {
        return;
    }

    function hidePreloader() {
        var preloader = document.querySelector(".preloader");
        if (!preloader) {
            return;
        }
        preloader.style.display = "none";
    }

    if (document.readyState === "interactive" || document.readyState === "complete") {
        hidePreloader();
    }

    document.addEventListener("DOMContentLoaded", hidePreloader);
    window.addEventListener("load", hidePreloader);

    // Last-resort timeout to avoid infinite overlay when other JS crashes.
    setTimeout(hidePreloader, 2500);
    window.addEventListener("error", hidePreloader);
})();

/* ---------- STRING ---------- */
function isEmpty(value) {
    return !value || value.trim().length === 0;
}

function trimValue(inputElement) {
    if (inputElement && inputElement.value) {
        inputElement.value = inputElement.value.trim();
    }
}

/* ---------- EMAIL ---------- */
function isValidEmail(email) {
	const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regex.test(email);
}

/* ---------- SPACE BLOCK ---------- */
function blockSpace(event){
	if (event.key === " "){
		event.preventDefault();
	}
}

/* ---------- BUTTON ---------- */
function disableButton(btn) {
    if (btn) btn.disabled = true;
}

function enableButton(btn) {
    if (btn) btn.disabled = false;
}

/* ---------- MESSAGE ---------- */
function showMessage(element, text, color = "red") {
    if (!element) return;
    element.textContent = text;
    element.style.color = color;
}

function clearMessage(element) {
    if (!element) return;
    element.textContent = "";
}

/* ---------- INPUT STYLE ---------- */
function setInputError(input) {
    if (input) input.classList.add("input-error");
}

function clearInputError(input) {
    if (input) input.classList.remove("input-error");
}

/* ---------- ASCII Check (No Vietnamese, no emoji) ---------- */
function isAsciiOnly(text){
	return /^[\x00-\x7F]*$/.test(text);
}

/* ---------- PASSWORD BASIC CHECK ---------- */

function hasLetter(text){
	return /[A-Za-z]/.test(text);
}

function hasNumber(text){
	return /[0-9]/.test(text);
}

function hasSpace(text){
	return /\s/.test(text);
}

/* ---------- CENTRAL MODAL POPUPS ---------- */

(function () {
    if (typeof document === "undefined") return;

    // Inject Modal Styles
    const modalStyle = document.createElement("style");
    modalStyle.innerHTML = `
        #modal-overlay {
            position: fixed !important;
            top: 0 !important;
            left: 0 !important;
            width: 100vw !important;
            height: 100vh !important;
            background: rgba(0, 0, 0, 0.6) !important;
            display: flex !important;
            align-items: center !important;
            justify-content: center !important;
            z-index: 100000 !important;
            opacity: 0;
            transition: opacity 0.3s ease;
            pointer-events: auto !important;
        }
        #modal-overlay.show {
            opacity: 1 !important;
        }
        .custom-modal-box {
            background: #ffffff !important;
            width: 90% !important;
            max-width: 400px !important;
            border-radius: 8px !important;
            padding: 30px 24px !important;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2) !important;
            text-align: center !important;
            transform: scale(0.8) !important;
            transition: transform 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275) !important;
            box-sizing: border-box !important;
            display: block !important;
        }
        #modal-overlay.show .custom-modal-box {
            transform: scale(1) !important;
        }
        .modal-icon-circle {
            width: 64px !important;
            height: 64px !important;
            border-radius: 50% !important;
            display: flex !important;
            align-items: center !important;
            justify-content: center !important;
            margin: 0 auto 20px !important;
            font-size: 30px !important;
            box-sizing: border-box !important;
        }
        .modal-icon-circle.success {
            background: #e8f8f0 !important;
            color: #2ecc71 !important;
            border: 2px solid #2ecc71 !important;
        }
        .modal-icon-circle.error {
            background: #fde8e8 !important;
            color: #e74c3c !important;
            border: 2px solid #e74c3c !important;
        }
        .modal-title {
            font-size: 20px !important;
            font-weight: 700 !important;
            color: #2c3e50 !important;
            margin-bottom: 12px !important;
            font-family: inherit !important;
        }
        .modal-desc {
            font-size: 14px !important;
            color: #7f8c8d !important;
            line-height: 1.5 !important;
            margin-bottom: 24px !important;
            font-family: inherit !important;
        }
        .modal-btn {
            border: none !important;
            border-radius: 6px !important;
            padding: 10px 30px !important;
            font-size: 15px !important;
            font-weight: 600 !important;
            cursor: pointer !important;
            transition: background-color 0.2s ease !important;
            outline: none !important;
            display: inline-block !important;
        }
        .modal-btn.success {
            background: #2ecc71 !important;
            color: #ffffff !important;
        }
        .modal-btn.success:hover {
            background: #27ae60 !important;
        }
        .modal-btn.error {
            background: #e74c3c !important;
            color: #ffffff !important;
        }
        .modal-btn.error:hover {
            background: #c0392b !important;
        }
    `;
    document.head.appendChild(modalStyle);
})();

function showModal(message, type = "success") {
    if (typeof document === "undefined" || !message || message.trim() === "") return;

    // Remove old overlay if exists
    const oldOverlay = document.getElementById("modal-overlay");
    if (oldOverlay) oldOverlay.remove();

    // Create overlay
    const overlay = document.createElement("div");
    overlay.id = "modal-overlay";

    // Set text details based on type
    const titleText = type === "success" ? "Thực hiện thành công!" : "Thông báo!";
    const iconClass = type === "success" ? "fa fa-check" : "fa fa-exclamation";
    const btnClass = type === "success" ? "success" : "error";

    overlay.innerHTML = `
        <div class="custom-modal-box">
            <div class="modal-icon-circle ${type}">
                <i class="${iconClass}"></i>
            </div>
            <div class="modal-title">${titleText}</div>
            <div class="modal-desc">${message}</div>
            <button class="modal-btn ${btnClass}">OK</button>
        </div>
    `;

    document.body.appendChild(overlay);

    // Fade in
    setTimeout(() => {
        overlay.classList.add("show");
    }, 10);

    // Close function
    const closeModal = () => {
        overlay.classList.remove("show");
        setTimeout(() => {
            overlay.remove();
        }, 300);
    };

    // Close on OK button click ONLY
    overlay.querySelector(".modal-btn").addEventListener("click", closeModal);
}

// Keep showToast as an alias to avoid breaking existing JSP files
function showToast(message, type = "success") {
    showModal(message, type);
}
