export async function showAPIData() {

  //   TODO: which port to use?
  let uri = "http://192.168.160.87:50008/sensor/all/latest-readings";

  const request = async () => {
    let dataFromAPI;
    await fetch(uri)
      .then((resp) => resp.json()) // Transform the data into json
      .then(function (data) {
        console.log("Data from API\n")
        console.log(data);
        dataFromAPI = data;
      })
      .catch(function (error) {
        console.log(error.toString());
      });

    return dataFromAPI;
  };

  return request();
}