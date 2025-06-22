const filterForm = document.getElementById('filterForm');
const applyFiltersBtn = document.getElementById('applyFilters');
const studentsContainer = document.getElementById('studentsContainer');
const studentCount = document.getElementById('studentCount');
const addStudentBtn = document.getElementById('addStudentBtn');
const addStudentModal = document.getElementById('addStudentModal');
const closeModal = document.querySelector('.close');
const addStudentForm = document.getElementById('addStudentForm');

document.addEventListener('DOMContentLoaded', loadStudents);
applyFiltersBtn.addEventListener('click', loadStudents);
addStudentBtn.addEventListener('click', () => addStudentModal.style.display = 'block');
closeModal.addEventListener('click', () => addStudentModal.style.display = 'none');
addStudentForm.addEventListener('submit', handleAddStudent);

window.addEventListener('click', (e) => {
    if (e.target === addStudentModal) {
        addStudentModal.style.display = 'none';
    }
});

function loadStudents() {
    studentCount.textContent = 'Loading students...';
    studentsContainer.innerHTML = '<p>Loading student data...</p>';

    const params = new URLSearchParams();
    const formData = new FormData(filterForm);

    ['faculty', 'course', 'scholarship', 'group', 'age'].forEach(param => {
        const value = formData.get(param);
        if (value !== null && value !== '') {
            params.append(param, value);
        }
    });

    const sortField = formData.get('sortField');
    const sortDirection = formData.get('sortDirection');

    if (sortField) {
        params.append('sort', `${sortField},${sortDirection}`);
    }

    fetch(`/api/students?${params.toString()}`)
        .then(response => {
            if (!response.ok) throw new Error('Server response was not ok');
            return response.json();
        })
        .then(students => {
            renderStudents(students);
            studentCount.textContent = `Found ${students.length} ${students.length === 1 ? 'student' : 'students'}`;
        })
        .catch(error => {
            console.error('Error loading students:', error);
            studentsContainer.innerHTML = `<div class="error">Failed to load students: ${error.message}</div>`;
            studentCount.textContent = 'Error loading students';
        });
}


function renderStudents(students) {
    if (!students || students.length === 0) {
        studentsContainer.innerHTML = '<p>No students found. Try changing your filters.</p>';
        return;
    }

    const studentsHTML = students.map(student => `
        <div class="student-card">
            <h3>${student.firstName} ${student.lastName}</h3>
            <div class="student-info">
                <p><strong>Faculty:</strong> ${student.faculty}</p>
                <p><strong>Course:</strong> ${student.course}</p>
                <p><strong>Group:</strong> ${student.group}</p>
                <p><strong>Age:</strong> ${student.age}</p>
                <p><strong>Scholarship:</strong> ${student.scholarship ? 'Yes' : 'No'}</p>
            </div>
            <button class="delete-btn" onclick="deleteStudent('${student.id}')">Delete</button>
        </div>
    `).join('');

    studentsContainer.innerHTML = `<div class="student-grid">${studentsHTML}</div>`;
}

function handleAddStudent(e) {
    e.preventDefault();

    const formData = new FormData(addStudentForm);
    const studentData = {
        firstName: formData.get('firstName'),
        lastName: formData.get('lastName'),
        faculty: formData.get('faculty'),
        course: parseInt(formData.get('course')),
        group: parseInt(formData.get('group')),
        age: parseInt(formData.get('age')),
        scholarship: formData.get('scholarship') === 'true'
    };

    fetch('/api/students', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(studentData)
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to add student');
            return response.json();
        })
        .then(() => {
            addStudentForm.reset();
            addStudentModal.style.display = 'none';
            loadStudents();
            alert('Student added successfully!');
        })
        .catch(error => {
            console.error('Error adding student:', error);
            alert(`Error: ${error.message}`);
        });
}

function deleteStudent(id) {
    if (!confirm('Are you sure you want to delete this student?')) return;

    fetch(`/api/students/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to delete student');
            loadStudents();
            alert('Student deleted successfully!');
        })
        .catch(error => {
            console.error('Error deleting student:', error);
            alert(`Error: ${error.message}`);
        });
}