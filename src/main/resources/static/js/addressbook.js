document.addEventListener('DOMContentLoaded', function() {
    // Register handlers
    const createForm = document.getElementById('createBookForm');
    if (createForm) createForm.addEventListener('submit', createBook);

    // initial load
    fetchBooks();
});

async function fetchBooks() {
    const out = document.getElementById('booksList');
    out.innerHTML = 'Loading...';
    try {
        const res = await fetch('/api/addressBooks');
        const books = await res.json();
        renderBooks(out, books);
    } catch (e) {
        out.innerHTML = 'Failed to load books: ' + e;
    }
}

function renderBooks(container, books) {
    if (!books || books.length === 0) {
        container.innerHTML = '<p>No books yet.</p>';
        return;
    }
    container.innerHTML = '';
    books.forEach(book => {
        const box = document.createElement('div');
        box.className = 'book';
        box.innerHTML = `<h3>${escapeHtml(book.name)} (id: ${book.id})</h3>`;
        // buddies
        const ul = document.createElement('ul');
        if (book.buddies && book.buddies.length) {
            book.buddies.forEach(b => {
                const li = document.createElement('li');
                li.textContent = `${b.name} — ${b.phone || ''}`;
                ul.appendChild(li);
            });
        } else {
            const li = document.createElement('li'); li.textContent = 'No buddies.'; ul.appendChild(li);
        }
        box.appendChild(ul);

        // small form to add buddy via AJAX
        const form = document.createElement('form');
        form.innerHTML = `
      <input name="name" placeholder="Buddy name" required />
      <input name="phone" placeholder="Phone" />
      <input name="address" placeholder="Address" />
      <button type="submit">Add buddy</button>
    `;
        form.addEventListener('submit', ev => {
            ev.preventDefault();
            const data = {
                name: form.elements['name'].value,
                phone: form.elements['phone'].value,
                address: form.elements['address'].value
            };
            addBuddy(book.id, data);
        });
        box.appendChild(form);
        container.appendChild(box);
    });
}

async function createBook(ev) {
    ev.preventDefault();
    const form = ev.target;
    const name = form.elements['name'].value;
    const payload = { name };
    const res = await fetch('/api/addressBooks', {
        method: 'POST',
        headers: { 'Content-Type':'application/json' },
        body: JSON.stringify(payload)
    });
    if (res.ok) {
        form.reset();
        await fetchBooks();
    } else {
        alert('Failed to create book');
    }
}

async function addBuddy(bookId, buddy) {
    const res = await fetch(`/api/addressBooks/${bookId}/buddies`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(buddy)
    });
    if (res.ok) {
        await fetchBooks();
    } else {
        alert('Failed to add buddy');
    }
}

function escapeHtml(s) {
    return (s || '').replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
}
