const AUTH_API = "http://localhost:8080/api/auth";
const SWEETS_API = "http://localhost:8080/api/sweets";

// UI Elements
const authSection = document.getElementById("authSection");
const dashboardSection = document.getElementById("dashboardSection");
const loginCard = document.querySelector(".login-card");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const sweetsGrid = document.getElementById("sweetsGrid");

let sweetsList = [];

function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("jwtToken")
    };
}

const COLORS = ['#FFB7B2', '#FFDAC1', '#E2F0CB', '#B5EAD7', '#C7CEEA', '#F4A261', '#E76F51', '#f8c291'];
function getSweetColor(str) {
    let hash = 0;
    for (let i = 0; i < str.length; i++) hash = str.charCodeAt(i) + ((hash << 5) - hash);
    return COLORS[Math.abs(hash) % COLORS.length];
}

// --- AUTHENTICATION WITH SMOOTH TRANSITIONS ---

document.getElementById("loginBtn").addEventListener("click", async () => {
    if (!usernameInput.value || !passwordInput.value) return alert("Please fill all fields!");
    try {
        const response = await fetch(`${AUTH_API}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username: usernameInput.value, password: passwordInput.value })
        });
        
        if (response.ok) {
            const data = await response.json(); 
            localStorage.setItem("jwtToken", data.token); 
            
            // 1. Start the Exit Animation for Login Card
            loginCard.classList.add("fade-out");

            // 2. Wait for animation to finish (500ms) before switching
            setTimeout(() => {
                authSection.style.display = "none";
                loginCard.classList.remove("fade-out"); // Reset for next time
                
                dashboardSection.style.display = "block";
                fetchSweets(); 
            }, 500);
            
        } else {
            alert("Invalid Credentials");
        }
    } catch (error) { alert("Action failed!"); }
});

document.getElementById("registerBtn").addEventListener("click", async () => {
    if (!usernameInput.value || !passwordInput.value) return alert("Please fill all fields!");
    try {
        const response = await fetch(`${AUTH_API}/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username: usernameInput.value, password: passwordInput.value })
        });
        if (response.ok) alert("Registered! Now click Login.");
        else alert("Error registering.");
    } catch (error) { alert("Action failed!"); }
});

document.getElementById("logoutBtn").addEventListener("click", () => {
    // 1. Start Exit Animation for Dashboard
    dashboardSection.classList.add("fade-out-dashboard");

    // 2. Wait for animation to finish before showing login
    setTimeout(() => {
        localStorage.removeItem("jwtToken");
        dashboardSection.style.display = "none";
        dashboardSection.classList.remove("fade-out-dashboard"); // Reset
        
        authSection.style.display = "flex";
        usernameInput.value = "";
        passwordInput.value = "";
    }, 400);
});

// --- DASHBOARD LOGIC ---

async function fetchSweets() {
    try {
        const response = await fetch(SWEETS_API, { headers: getAuthHeaders() });
        if (response.ok) {
            sweetsList = await response.json();
            renderSweets();
        }
    } catch (error) { console.error("Fetch failed", error); }
}

function renderSweets() {
    const searchQuery = document.getElementById("searchInput").value.toLowerCase();
    const sortType = document.getElementById("sortSelect").value;
    
    let filteredSweets = sweetsList.filter(s => s.name.toLowerCase().includes(searchQuery));
    if (sortType === "asc") filteredSweets.sort((a, b) => a.price - b.price);
    else if (sortType === "desc") filteredSweets.sort((a, b) => b.price - a.price);

    sweetsGrid.innerHTML = "";
    if (filteredSweets.length === 0) {
        sweetsGrid.innerHTML = `<div style="text-align: center; color: #555; width: 100%;">No sweets found 🍩</div>`;
        return;
    }

    filteredSweets.forEach(sweet => {
        const bgColor = getSweetColor(sweet.name);
        sweetsGrid.innerHTML += `
            <div class="card">
                <div class="card-header" style="background-color: ${bgColor}">
                    <h3 class="sweet-name">${sweet.name}</h3>
                </div>
                <div class="card-content">
                    <span class="tag">In Stock: ${sweet.quantity}</span>
                    <div class="price">₹${sweet.price}</div>
                    <div class="action-buttons">
                        <button class="btn-purchase" ${sweet.quantity <= 0 ? "disabled" : ""} onclick="handlePurchase(${sweet.id})">Purchase</button>
                        <button class="btn-delete" onclick="handleDelete(${sweet.id})">Delete</button>
                    </div>
                </div>
            </div>`;
    });
}

document.getElementById("addBtn").addEventListener("click", async () => {
    const name = document.getElementById("addName").value;
    const price = document.getElementById("addPrice").value;
    const quantity = document.getElementById("addQty").value;
    if (!name || !price || !quantity) return alert("Fill all fields!");
    try {
        await fetch(SWEETS_API, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify({ name, price: parseFloat(price), quantity: parseInt(quantity), category: "General" })
        });
        document.getElementById("addName").value = "";
        document.getElementById("addPrice").value = "";
        document.getElementById("addQty").value = "";
        fetchSweets();
    } catch (error) { alert("Add failed"); }
});

window.handlePurchase = async function(id) {
    try {
        await fetch(`${SWEETS_API}/${id}/purchase`, { method: "POST", headers: getAuthHeaders() });
        fetchSweets();
    } catch (error) { alert("Error"); }
};

window.handleDelete = async function(id) {
    if (confirm("Delete?")) {
        try {
            await fetch(`${SWEETS_API}/${id}`, { method: "DELETE", headers: getAuthHeaders() });
            fetchSweets();
        } catch (error) { alert("Delete failed"); }
    }
};

document.getElementById("searchInput").addEventListener("input", renderSweets);
document.getElementById("sortSelect").addEventListener("change", renderSweets);