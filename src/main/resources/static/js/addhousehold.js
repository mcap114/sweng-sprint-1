var memberCount = 0;
var petCount = 0;

function addMember() {
    memberCount++;
    var container = document.createElement("div");
    container.id = `member-${memberCount}`;
    container.classList.add("fields");
    container.innerHTML = `
        <div class="heading-2">
            <p>Household Member</p>
            <div class="delete-button" onclick="deleteMember(${memberCount})">
                <img class="delete-button" src="/png/close-icon-x.png">
                <span class="delete-button">Delete Member</span>
            </div>
        </div>
        <div class="main-respondent-body">
            <div class="image-panel">
                <div class="image-field">
                    <label>Upload Picture</label>
                    <div id="preview" class="image-display"> 
                        <img class="image-display" src="png/avatar.jpg" alt="Default Avatar" id="avatar-${memberCount}">
                    </div>
                    <label for="resPfp-${memberCount}" class="custom-file-upload">Choose File</label>
                    <input class="custom-file-upload" type="file" id="resPfp-${memberCount}" name="resPfp" accept="image/jpeg, image/png" onchange="upload(event, 'avatar-${memberCount}')" required/>
                </div>
            </div>
            <div class="text-panel">
                <div class="form-row">
                    <div class="input-field">
                        <label>Last Name</label>
                        <input type="text" name="resLastName" required/>
                    </div>
                    <div class="input-field">
                        <label>First Name</label>
                        <input type="text" name="resFirstName" required/>
                    </div>
                    <div class="input-field">
                        <label>Middle Name</label>
                        <input type="text" name="resMiddleName" placeholder="Type &quot;-&quot; if N/A" required/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="input-field">
                        <label>Gender</label>
                        <select id="resGender" name="resGender" required>
                            <option disabled selected value>---</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    </div>
                    <div class="input-field">
                        <label>Birthday</label>
                        <input type="date" name="resBirthday" required/>
                    </div>
                    <div class="input-field">
                        <label>Civil Status</label>
                        <select id="resCivilStatus" name="resCivilStatus" required>
                            <option disabled selected value>---</option>
                            <option value="Single">Single</option>
                            <option value="Married">Married</option>
                            <option value="Separated">Separated</option>
                            <option value="Widowed">Widowed</option>
                        </select>
                    </div>
                    <div class="input-field">
                        <label>Contact Number</label>
                        <input type="tel" name="resContactNumber" placeholder="09XX XXX XXXX" pattern="09[0-9]{2} [0-9]{3} [0-9]{4}" required/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="input-field">
                        <label>Highest Educational Attainment</label>
                        <select id="resHighestEducationalAttainment" name="resHighestEducationalAttainment" required>
                            <option disabled selected value>---</option>
                            <option value="No Education">No Education</option>
                            <option value="Elementary Undergraduate">Elementary Undergraduate</option>
                            <option value="Elementary Graduate">Elementary Graduate</option>
                            <option value="Highschool Undergraduate">High School Undergraduate</option>
                            <option value="Highschool Graduate">High School Graduate</option>
                            <option value="College Undergraduate">College Undergraduate</option>
                            <option value="College Graduate Or Higher">College Graduate or Higher</option>
                        </select>
                    </div>
                    <div class="input-field">
                        <label>Occupation</label>
                        <input type="text" name="resOccupation" required/>
                    </div>
                    <div class="input-field">
                        <label>Monthly Income</label>
                        <input type="number" name="resMonthlyIncome" placeholder="Type &quot;0&quot; if Not Applicable" required/>
                    </div>
                    <div class="input-field">
                        <label>Relationship to Main Respondent</label>
                        <input type="text" name="resRelationToRespondent" required/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="input-field">
                        <label>Medical Complications</label>
                        <input type="text" name="resHealthStatus" placeholder="Type N/A if none" required/>
                    </div>
                    <div class="input-field">
                        <label>PWD Type</label>
                        <input type="text" name="resPwdType" placeholder="Type N/A if none" required/>
                    </div>
                </div>
            </div>
        </div>
        <hr>
    `;
    document.getElementById("additional-fields").appendChild(container);
    attachFileInputListeners(container);
}

function attachFileInputListeners(container) {
    const fileInputs = container.querySelectorAll('.files-input');
    fileInputs.forEach(input => {
        input.addEventListener('mousedown', setPrevData);
        input.addEventListener('change', handleFile);
    });
}

function addPet() {
    petCount++;
    var container = document.createElement("div");
    container.id = `pet-${petCount}`;
    container.classList.add("fields");
    container.innerHTML = `
        <div class="heading-2">
            <p>Household Pet</p>
            <div class="delete-button" onclick="deletePet(${petCount})">
                <img class="delete-button" src="/png/close-icon-x.png">
                <span class="delete-button">Delete Pet</span>
            </div>
        </div>
        <div class="main-respondent-body">
            <div class="image-panel">
                <div class="image-field">
                    <label>Upload Picture</label>
                    <div id="preview"> 
                        <img class="pet-image-display" src="png/avatar.jpg" alt="Default Avatar" id="avatar-pet-${petCount}">
                    </div>
                    <label for="resPfp-pet-${petCount}" class="custom-file-upload">Choose File</label>
                    <input class="custom-file-upload" type="file" id="resPfp-pet-${petCount}" name="petPfp" accept="image/jpeg, image/png" onchange="upload(event, 'avatar-pet-${petCount}')" required/>
                </div>
            </div>
            <div class="form-row">
                <div class="input-field">
                    <label>Pet Name</label>
                    <input type="text" name="petName" required/>
                </div>
                <div class="input-field">
                    <label>Animal Type</label>
                    <input type="text" name="petAnimalType" required/>
                </div>
            </div>
        </div>
        <hr>
    `;
    document.getElementById("additional-fields").appendChild(container);
}

// upload image 
function upload(event, memberId) {
  const fileInput = event.target;
  const member = document.getElementById(memberId);

  const file = fileInput.files[0];
  if (file) {
      const reader = new FileReader();

      reader.onload = function(e) {
          member.src = e.target.result;
      }

      reader.readAsDataURL(file);
  }
}

function deleteMember(memberCount) {
    
    var elem = document.getElementById(`member-${memberCount}`);
    elem.parentNode.removeChild(elem);
    return false;
}

function deletePet(petCount) {
    var elem = document.getElementById(`pet-${petCount}`);
    elem.parentNode.removeChild(elem);
    return false;
}

// const fInputs = document.querySelectorAll('.btcd-f-input>div>input');
// let fileList = { files: [] };
// let fName = null;
// let mxSiz = null;

// const getFileSize = (size) => {
//     const units = ['Bytes', 'KB', 'MB', 'GB'];
//     let unitIndex = 0;
//     while (size > 900) { size /= 1024; unitIndex++; }
//     return `${(Math.round(size * 100) / 100)} ${units[unitIndex]}`;
// }

// const setPrevData = (e) => {
//     if (e.target.hasAttribute('multiple') && fName !== e.target.name) {
//         console.log('multiple');
//         fName = e.target.name;
//         fileList.files = [...e.target.files];
//     }
// }

// const handleFile = (e) => {
//     const err = [];
//     mxSiz = e.target.closest('.btcd-f-input').querySelector('.f-max');
//     mxSiz = mxSiz ? Number(mxSiz.innerHTML.replace(/\D/g, '')) * Math.pow(1024, 2) : null;
    
//     const newFiles = [...e.target.files];
//     if (e.target.hasAttribute('multiple')) {
//         fileList.files.push(...newFiles);
//     } else {
//         fileList.files = [newFiles[0]];
//     }

//     if (mxSiz > 0) {
//         fileList.files = fileList.files.filter(file => {
//             if (file.size < mxSiz) {
//                 mxSiz -= file.size;
//                 return true;
//             } else {
//                 console.log('rejected', file.size);
//                 err.push('Max Upload Size Exceeded');
//                 return false;
//             }
//         });
//     }

//     e.target.files = createFileList(...fileList.files);
//     updateFileListView(e.target);
// }

// const updateFileListView = (inputElement) => {
//     const fileCount = inputElement.files.length;
//     const fileTitle = fileCount > 0 ? `${fileCount} File Selected` : 'No File Chosen';
//     inputElement.closest('.btcd-f-input').querySelector('.btcd-f-title').innerText = fileTitle;

//     const fileContainer = inputElement.closest('.btcd-f-input').querySelector('.btcd-files');
//     fileContainer.innerHTML = '';
//     for (let i = 0; i < fileCount; i++) {
//         const file = inputElement.files[i];
//         const fileTypeIcon = file.type.match(/image-*/) ? window.URL.createObjectURL(file) : "/svg/add-file.svg";
//         fileContainer.insertAdjacentHTML('beforeend', `
//             <div>
//                 <img src="${fileTypeIcon}" alt="img" title="${file.name}">
//                 <div>
//                     <span title="${file.name}">${file.name}</span>
//                     <br/>
//                     <small>${getFileSize(file.size)}</small>
//                 </div>
//             </div>
//         `);
//     }
// }

// const createFileList = (...files) => {
//     const dataTransfer = new DataTransfer();
//     files.forEach(file => dataTransfer.items.add(file));
//     return dataTransfer.files;
// }

// fInputs.forEach(input => {
//     input.addEventListener('mousedown', setPrevData);
//     input.addEventListener('change', handleFile);
// });
